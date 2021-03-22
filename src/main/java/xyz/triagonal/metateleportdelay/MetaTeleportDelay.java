package xyz.triagonal.metateleportdelay;

import net.ess3.api.IEssentials;
import net.ess3.api.events.teleport.TeleportWarmupEvent;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class MetaTeleportDelay extends JavaPlugin implements Listener {

    private static final String META_KEY = "essx-teleport-delay";

    private IEssentials essentials;
    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("Essentials");
        if (plugin instanceof IEssentials) {
            essentials = (IEssentials)plugin;
        } else {
            this.getLogger().severe("Error: EssentialsX could not be hooked.");
            this.getLogger().severe("Please ensure that EssentialsX is installed and up-to-date. https://essentialsx.net/downloads.html");
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        } else {
            this.getLogger().severe("Error: LuckPerms could not be hooked.");
            this.getLogger().severe("Please ensure that LuckPerms is installed and up-to-date. https://luckperms.net/download");
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // ¯\_(ツ)_/¯
    }

    @EventHandler
    public void onTeleportWarmup(TeleportWarmupEvent event) {
        if (event.getTeleportee() == null) {
            return;
        }

        Player teleportee = event.getTeleportee().getBase();
        if (!teleportee.isOnline()) {
            return; // is this even possible?
        }

        User lpUser = luckPerms.getUserManager().getUser(teleportee.getUniqueId());
        if (lpUser == null) {
            return;
        }

        double initialDelay = event.getDelay();
        double newDelay = initialDelay;

        try {
            newDelay = lpUser.resolveInheritedNodes(NodeType.META, luckPerms.getContextManager().getQueryOptions(lpUser).orElse(luckPerms.getContextManager().getStaticQueryOptions()))
                    .stream()
                    .filter(node -> node.getMetaKey().equalsIgnoreCase(META_KEY))
                    .mapToDouble(node -> Double.parseDouble(node.getMetaValue()))
                    .findFirst().orElse(initialDelay);
        } catch (NumberFormatException e) {
            this.getLogger().log(Level.SEVERE, "Meta value for key " + META_KEY + " was not a number.", e);
        }

        event.setDelay(newDelay);
        if (essentials.getSettings().isDebug()) {
            this.getLogger().info(String.format("Teleport delay modified: %f -> %f", initialDelay, newDelay));
        }
    }

}

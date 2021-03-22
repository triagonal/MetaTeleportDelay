# MetaTeleportDelay

MetaTeleportDelay is a small plugin to allow for customizable teleport delay times based on user/group meta. This plugin was originally written for members of the [M.O.S.S. discord server](https://discord.gg/PHpuzZS), and has been made available here and on Spigot for others to use.

Requires EssentialsX `2.18.0` or above and a recent version of LuckPerms.

## Usage

MetaTeleportDelay uses LuckPerms' meta system to determine the teleport delay for a user. Set user or group based meta with the key `essx-teleport-delay` to define a delay. The value of the meta pair will always override the value in the EssentialsX config, even if the values are the same.

If you don't know how LuckPerms' meta works, refer to their wiki: https://luckperms.net/wiki/Prefixes,-Suffixes-&-Meta and https://luckperms.net/wiki/Meta-Commands.

Examples:
- `/lp group vip meta set essx-teleport-delay 5.5` will give the `vip` group a teleport delay of 5.5 seconds.
- `/lp user epicgamer420 meta set essx-teleport-delay 1` will give the player `epicgamer420` a teleport delay of 1 second.
- `/lp group default meta set essx-teleport-delay 10 dimension-type=the_nether` will give the `default` group a teleport delay of 10 seconds if they are currently inside the nether dimension.
- `/lp group admin meta set essx-teleport-delay 0` will give the `admin` group a teleport delay of 0 seconds, which makes their teleports instant.

Players or teleport sources with `essentials.teleport.timer.bypass` are able to bypass the teleport delay as normal.

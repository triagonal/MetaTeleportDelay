# MetaTeleportDelay

MetaTeleportDelay is a small plugin to allow for customizable teleport delay times based on user/group meta.

Requires EssentialsX (`2.18.0` or above) and LuckPerms.

## Usage

MetaTeleportDelay uses LuckPerms' meta system to determine the teleport delay for a user. Set user or group based meta with the key `essx-teleport-delay` to define a delay. The value of the meta pair will always override the value in the EssentialsX config, even if the values are the same.

E.g. `/lp group vip meta set essx-teleport-delay 5.5` will give the `vip` group a teleport delay of 5.5 seconds.

Players or teleport sources with `essentials.teleport.timer.bypass` are able to bypass the teleport delay as normal.

package com.ashkiano.ringofdayandnight;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class RingOfDayAndNight extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("givering").setExecutor(this);

        Metrics metrics = new Metrics(this, 19469);

        this.getLogger().info("Thank you for using the RingOfDayAndNight plugin! If you enjoy using this plugin, please consider making a donation to support the development. You can donate at: https://donate.ashkiano.com");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("givering") && sender instanceof Player) {
            Player player = (Player) sender;
            ItemStack ring = new ItemStack(Material.CLOCK);
            ItemMeta meta = ring.getItemMeta();
            meta.setDisplayName("Ring of Day and Night");
            List<String> lore = new ArrayList<>();
            lore.add("With this ring, control the cycle of day and night.");
            meta.setLore(lore);
            ring.setItemMeta(meta);
            player.getInventory().addItem(ring);
            player.sendMessage("You've received the Ring of Day and Night!");
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore()) {
            List<String> lore = itemInHand.getItemMeta().getLore();
            if (lore.contains("With this ring, control the cycle of day and night.")) {
                World world = player.getWorld();
                long time = world.getTime();

                if (time >= 0 && time < 12000) {
                    world.setTime(13000);
                } else {
                    world.setTime(1000);
                }
            }
        }
    }
}

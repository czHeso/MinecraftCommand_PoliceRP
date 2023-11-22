package cz.hesovodoupe.policeroleplay

import cz.hesovodoupe.policerp.databaze
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender


object prestupek: CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, p2: String, args: Array<out String>?): Boolean {

        if (!sender.hasPermission("policerp.punish")) {
            sender.sendMessage("Na tohle nemáš práva!")
            return false;

        }

        if (args == null)
        {
            sender.sendMessage("Musíš napsat nick toho, kdo udělal přestupek.")
            return false
        }
         sender.sendMessage(" ")

         val trestany = args?.get(0).toString()
         val hodnota = 1
         println("$sender issued command to $cmd")

         if(!databaze.containsKey(trestany))
         {

             databaze[trestany] = hodnota
             sender.sendMessage("Toto je přestupek hráče $trestany! Děkujeme za nahlášení.")
             Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "msg $trestany Nyní máš první nahlášený přestupek od $sender")

         }
         else {
             var jizTrestanHodnota: Int = databaze[trestany]!!
             jizTrestanHodnota = jizTrestanHodnota + 1
             sender.sendMessage("Toto je $jizTrestanHodnota přestupek hráče $trestany!")
             Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "msg $trestany Nyní máš $jizTrestanHodnota přestupků. Poslední nahlášený od $sender")

             if(jizTrestanHodnota >9)
             {
                 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "jail $trestany 10m Policie 1")
                 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "msg $trestany Již máš $jizTrestanHodnota přestupků. Jdeš do jailu!")
                 jizTrestanHodnota = 0
             }

             databaze[trestany] = jizTrestanHodnota

         }





        return true
    }


}
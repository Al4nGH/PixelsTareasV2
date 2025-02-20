package me.alanelol.pixelstareasv2.utils;

import me.alanelol.pixelstareasv2.PixelsTareasV2;
import me.alanelol.pixelstareasv2.abstractos.Tareas;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class PlayerInfo {
    public PlayerInfo(UUID uuid, PixelsTareasV2 plugin, Player player)
    {
        this.plugin = plugin;
        log("CONSTRUCTING FOR " + player.getName());
        this.uuid = uuid;
        this.player = player;

        DataHandler dataHandler = new DataHandler(plugin);

        // GET DATA FROM SAVE FILE
        log("GETTING DATA FROM SAVE FILE");
        tareaName = dataHandler.getTareaNameFromPlayer(player);
        tareaAssignedDay = dataHandler.getAssignedDay(player);
        blocksMined = dataHandler.getBlocksMined(player);
        playerKills = dataHandler.getPlayerKills(player);
        secondsPlayed = dataHandler.getSecondsPlayed(player);

        // actualizar tarea
        setTareaValues();
    }

    // GETTABLES (SETS AT START)
    private final PixelsTareasV2 plugin;
    private final UUID uuid;
    private final Player player;

    private String tareaName;
    private int tareaAssignedDay;
    private int secondsPlayed;

    // GETTABLES AND SETTABLES
    private HashMap<Material, Integer> blocksMined;
    private int playerKills;

    // CONSTANTS
    static final String TAREA_1 = "Tarea1";
    static final String TAREA_2 = "Tarea2";
    static final String TAREA_3 = "Tarea3";
    static final String TAREA_4 = "Tarea4";
    static final String TAREA_5 = "Tarea5";
    static final String TAREA_6 = "Tarea6";
    static final String TAREA_7 = "Tarea7";

    // REQUIRED
    private HashMap<Material, Integer> blocksRequired;
    private int killsRequired;
    private int secondsRequired;

    @Override
    public String toString()
    {
        return "uuid: " + uuid.toString() +
                " tareaName: " + tareaName +
                " assignedDay: " + tareaAssignedDay +
                " isSameDay: " + isSameDay() +
                " blocksMined: " + blocksMined +
                " playerKills: " + playerKills +
                " secondsPlayed: " + secondsPlayed;
    }

    public boolean isSameDay()
    {
        return tareaAssignedDay == Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    // TODO: MAKE THE STREAK ACTUALLY WORK AND CHANGE TASKS
    /**
     * Check if the task has been completed, and change the values
     */
    public void updateVariableStatuses()
    {
        setTareaValues();
        if (isSameDay())
        {
            if (completed())
            {
                log("completed in same day");
                rewardPlayer(player);
                switch (tareaName)
                {
                    case TAREA_1:
                        tareaName = TAREA_2;
                        break;
                    case TAREA_2:
                        tareaName = TAREA_3;
                        break;
                    case TAREA_3:
                        tareaName = TAREA_4;
                        break;
                    case TAREA_4:
                        tareaName = TAREA_5;
                        break;
                    case TAREA_5:
                        tareaName = TAREA_6;
                        break;
                    case TAREA_6:
                        tareaName = TAREA_7;
                        break;
                    case TAREA_7:
                        tareaName = TAREA_1;
                        break;
                }
                resetValues();
                tareaAssignedDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1;
            }
        }
        else
        {
            int checkDay;
            log(player.getName() + "is not the same day");
            // 0 1 2 3 4 5 6
            checkDay = tareaAssignedDay -1;
            if (checkDay == Calendar.getInstance().get(Calendar.DAY_OF_YEAR))
            {
                log("is the same day");
            }
            else
            {
                log("did not complete task :(");
                tareaName = TAREA_1;
                resetValues();
            }
        }
    }

    private void resetValues()
    {
        blocksMined.clear();
        blocksMined.put(Material.AIR, 0);
        secondsPlayed = 0;
        playerKills = 0;
    }

    private void setTareaValues()
    {
        Tareas tareas = new Tareas();
        switch (tareaName)
        {
            case "Tarea1":
                tareas.tarea1Values(this);
                break;
            case "Tarea2":
                tareas.tarea2Values(this);
                break;
            case "Tarea3":
                tareas.tarea3Values(this);
                break;
            case "Tarea4":
                tareas.tarea4Values(this);
                break;
            case "Tarea5":
                tareas.tarea5Values(this);
                break;
            case "Tarea6":
                tareas.tarea6Values(this);
                break;
            case "Tarea7":
                tareas.tarea7Values(this);
                break;
        }

    }

    private void rewardPlayer(Player player)
    {
        Tareas tareas = new Tareas();
        switch (tareaName)
        {
            case "Tarea1":
                tareas.tarea1Complete(player);
                break;
            case "Tarea2":
                tareas.tarea2Complete(player);
                break;
            case "Tarea3":
                tareas.tarea3Complete(player);
                break;
            case "Tarea4":
                tareas.tarea4Complete(player);
                break;
            case "Tarea5":
                tareas.tarea5Complete(player);
                break;
            case "Tarea6":
                tareas.tarea6Complete(player);
                break;
            case "Tarea7":
                tareas.tarea7Complete(player);
                break;
        }
    }

    private boolean completed()
    {
        setTareaValues();
        boolean EnoughKills = false;
        boolean EnoughPlaytime = false;
        boolean EnoughBlocks = false;

        // KILLS
        EnoughKills = playerKills >= killsRequired;

        // PLAYTIME
        EnoughPlaytime = secondsPlayed >= secondsRequired;

        // BLOCKS
        int i = 0;
        // loopear por todos los bloques requeridos
        for (Material requiredMaterial : blocksRequired.keySet())
        {
            // si ha minado el bloque requerido del index actual
            if (blocksMined.containsKey(requiredMaterial))
            {
                // si ha minado más o lo requerido exacto
                if (blocksMined.get(requiredMaterial) >= blocksRequired.get(requiredMaterial))
                {
                    i++; // sumar i
                }
            }
        }

        EnoughBlocks = i == blocksRequired.size();

        //log("EnoughKills: " + EnoughKills + " EnoughPlaytime: " + EnoughPlaytime + " EnoughBlocks: " + EnoughBlocks);
        return (EnoughKills && EnoughPlaytime && EnoughBlocks);
    }

    private void log(String string)
    {
        plugin.getLogger().info(string);
    }

    public int getBlockProgress(Material material)
    {
        setTareaValues();
        if (blocksRequired.containsKey(material))
        {
            if (blocksMined.get(material) == null)
            {
                return 0;
            }
            return (int) Math.clamp(Math.floor(((double) blocksMined.get(material) / (double) blocksRequired.get(material)) * 10) ,0, 10);
        }
        else
        {
            log("material " + material + " not found.");
            return 1;
        }
    }

    public int getKillsProgress()
    {
        setTareaValues();
        if (killsRequired == 0)
        {
            return 10;
        }
        return (int) Math.clamp(Math.floor(((double) playerKills / killsRequired) * 10), 0, 10);
    }

    public int getPlaytimeProgress()
    {
        setTareaValues();
        if (secondsRequired == 0)
        {
            return 10;
        }
        return (int) Math.clamp(Math.floor(((double) secondsPlayed / secondsRequired) * 10), 0, 10);
    }

    // GETTERS Y SETTERS
    public String getTareaName() {
        return tareaName;
    }

    public int getTareaAssignedDay() {
        return tareaAssignedDay;
    }

    public HashMap<Material, Integer> getBlocksMined() {
        return blocksMined;
    }

    public void setBlocksMined(HashMap<Material, Integer> blocksMined) {
        this.blocksMined = blocksMined;
    }

    public int getPlayerKills() {
        return playerKills;
    }

    public void setPlayerKills(int playerKills) {
        this.playerKills = playerKills;
    }

    public int getSecondsPlayed() {
        return secondsPlayed;
    }

    public void setSecondsPlayed(int secondsPlayed) {
        this.secondsPlayed = secondsPlayed;
    }

    public HashMap<Material, Integer> getBlocksRequired() {
        return blocksRequired;
    }

    public void setBlocksRequired(HashMap<Material, Integer> blocksRequired) {
        this.blocksRequired = blocksRequired;
    }

    public int getKillsRequired() {
        return killsRequired;
    }

    public void setKillsRequired(int killsRequired) {
        this.killsRequired = killsRequired;
    }

    public int getSecondsRequired() {
        return secondsRequired;
    }

    public void setSecondsRequired(int secondsRequired) {
        this.secondsRequired = secondsRequired;
    }
}
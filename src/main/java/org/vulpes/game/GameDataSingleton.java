package org.vulpes.game;

import java.util.HashMap;
import java.util.Map;

public class GameDataSingleton {
    public static Integer metal;
    public static Integer crystal;
    public static Integer deuter;

    public static Integer power;
    public static Integer population;
    public static Integer food;

    public static Map<String, Integer> buildingLevels = new HashMap<>();
    public static Map<String, Integer> techLevels = new HashMap<>();
    public static Map<String, Integer> shipCounts = new HashMap<>();
    public static Map<String, Integer> defencesCounts = new HashMap<>();
}

package com.yolotengo.gatewayApp.util;

import com.yolotengo.gatewayApp.dto.CountDTO;

import java.util.*;

/**
 * Created by dgallego on 26/10/2017.
 */
public class DnaUtils {

    private static volatile Map<Integer, Boolean> dnaMap = new TreeMap<>();
    private static volatile CountDTO cout;


    private static final List<String> FIRSTNAME_LIST = new ArrayList<>(
            Arrays.asList("Cyborg", "Beacon", "Lady", "PantalosnesCuadrados", "UltraSuperMega"));

    private static final List<String> LASTNAME_LIST = new ArrayList<>(
            Arrays.asList("Amish", "Patito", "Samurai", "Saurio", "Shemale"));


    private DnaUtils(){

    }

    public static CountDTO getCount(){
        return cout;
    }

    public static void setCount(CountDTO count){
        cout = count;
    }

    public static Map<Integer, Boolean> getDnaMap(){
        return dnaMap;
    }

    //Genero un nombre Aleatorio para ahorrarle trabajo a magneto
    public static String generateRandomName(){
        return FIRSTNAME_LIST.get(0 + (int)(Math.random() * 5)) + "-" + LASTNAME_LIST.get(0 + (int)(Math.random() * 5));
    }

}

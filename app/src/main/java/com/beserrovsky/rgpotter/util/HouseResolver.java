package com.beserrovsky.rgpotter.util;

import com.beserrovsky.rgpotter.R;

import java.util.ArrayList;
import java.util.List;

public final class HouseResolver {

    public static int TitleOf(String house_id) {

        if (house_id == null) return R.string.house_None;

        switch (house_id.toLowerCase()){
            case "gryffindor":
                return R.string.house_Gryffindor;
            case "hufflepuff":
                return R.string.house_Hufflepuff;
            case "ravenclaw":
                return R.string.house_Ravenclaw;
            case "slytherin":
                return R.string.house_Slytherin;
        }

        return R.string.house_None;
    }

    public static int ImageOf(String house_id) {

        if (house_id == null) return R.drawable.ic_hogwarts;

        switch (house_id.toLowerCase()){
            case "gryffindor":
                return R.drawable.ic_gryffindor;
            case "hufflepuff":
                return R.drawable.ic_hufflepuff;
            case "ravenclaw":
                return R.drawable.ic_ravenclaw;
            case "slytherin":
                return R.drawable.ic_slytherin;
        }

        return R.drawable.ic_hogwarts;
    }

    public static int ColorOf(String house_id) {

        if (house_id == null) return R.color.house_None;

        switch (house_id.toLowerCase()){
            case "gryffindor":
                return R.color.house_Gryffindor;
            case "hufflepuff":
                return R.color.house_Hufflepuff;
            case "ravenclaw":
                return R.color.house_Ravenclaw;
            case "slytherin":
                return R.color.house_Slytherin;
        }

        return R.color.house_None;
    }

    public List<String> getHouses() {
        List<String> houses = new ArrayList<String>();
        houses.add("gryffindor");
        houses.add("hufflepuff");
        houses.add("ravenclaw");
        houses.add("slytherin");
        return houses;
    }

}

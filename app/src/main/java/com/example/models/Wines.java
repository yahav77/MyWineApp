package com.example.models;

import com.example.new1.R;

import java.util.ArrayList;
import java.util.List;

public class Wines {

    public static final List<Wine> white = white();
    public static final List<Wine> red = red();
    public static final List<Wine> rose = rose();
    public static final List<Wine> sparkling = sparkling();


    private static  List<Wine> white(){
        List<Wine> lst = new ArrayList<>();
        lst.add(new Wine("Tokaji",150,"750ml",2015, R.drawable.white_one));
        lst.add(new Wine("Riesling White",150,"750ml",2015,R.drawable.white_two));
        lst.add(new Wine("Chardonna",70,"750ml",2017,R.drawable.white_three));
        lst.add(new Wine("Pinot grigio",80,"750ml",2020,R.drawable.white_four));
        return lst;
    }
    private static  List<Wine> red(){
        List<Wine> lst = new ArrayList<>();
        lst.add(new Wine("Pinot neur",150,"750ml",2013,R.drawable.red_one));
        lst.add(new Wine("Zinfandel",90,"750ml",2022,R.drawable.red_two));
        lst.add(new Wine("Merlot",140,"750ml",2020,R.drawable.red_three));
        lst.add(new Wine("Cabarnet Suveignoun",50,"750ml",2020,R.drawable.red_four));
        return lst;
    }
    private static  List<Wine> rose(){
        List<Wine> lst = new ArrayList<>();
        lst.add(new Wine("Josh cellars Rose",150,"750ml",2015,R.drawable.rose_one));
        lst.add(new Wine("Prophecy French Rose",70,"750ml",2018,R.drawable.rose_two));
        lst.add(new Wine("Miraval provence rose",130,"750ml",2017,R.drawable.rose_three));
        lst.add(new Wine("Chateu Minuty rose",45,"750ml",2023,R.drawable.rose_four));

        return lst;
    }
    private static  List<Wine> sparkling(){
        List<Wine> lst = new ArrayList<>();
        lst.add(new Wine("Prosecco",150,"750ml",2015,R.drawable.sparkling_one));
        lst.add(new Wine("Cava",120,"750ml",2020,R.drawable.sparkling_two));
        lst.add(new Wine("Lambrusko",110,"750ml",2020,R.drawable.sparkling_three));
        lst.add(new Wine("Cremant",45,"750ml",2020,R.drawable.sparkling_four));

        return lst;
    }
}

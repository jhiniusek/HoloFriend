package holo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Locale;

public class Save {
    int hunger;
    int tiredness;
    int currency;
    boolean goodRod;
    boolean superRod;
    boolean bed;
    int bedX;
    int bedY;
    boolean wardrobe;
    int wardrobeX;
    int wardrobeY;
    boolean radio;
    int radioX;
    int radioY;
    boolean chessTypeBeat;
    boolean kurokami;
    int skin;
    MyLocale locale;

    public Save(int hunger, int tiredness, int currency, boolean goodRod, boolean superRod, boolean bed, int bedX, int bedY, boolean wardrobe, int wardrobeX, int wardrobeY, boolean radio, int radioX, int radioY, boolean chessTypeBeat, boolean kurokami, int skin, MyLocale locale) {
        this.hunger = hunger;
        this.tiredness = tiredness;
        this.currency = currency;
        this.goodRod = goodRod;
        this.superRod = superRod;
        this.bed = bed;
        this.bedX = bedX;
        this.bedY = bedY;
        this.wardrobe = wardrobe;
        this.wardrobeX = wardrobeX;
        this.wardrobeY = wardrobeY;
        this.radio = radio;
        this.radioX = radioX;
        this.radioY = radioY;
        this.chessTypeBeat = chessTypeBeat;
        this.kurokami = kurokami;
        this.skin = skin;
        this.locale = locale;
    }

    public void Load(FriendStats stats){
        stats.setHunger(hunger);
        stats.setTiredness(tiredness);
        stats.setCurrency(currency);
        stats.setGoodRod(goodRod);
        stats.setSuperRod(superRod);
        stats.setBedOwned(bed);
        stats.setBedPositionX(bedX);
        stats.setBedPositionY(bedY);
        stats.setWardrobeOwned(wardrobe);
        stats.setWardrobePositionX(wardrobeX);
        stats.setWardrobePositionY(wardrobeY);
        stats.setRadioOwned(radio);
        stats.setRadioPositionX(radioX);
        stats.setRadioPositionY(radioY);
        stats.setChessSlowed(chessTypeBeat);
        stats.setKurokami(kurokami);
        stats.setSkin(skin);
        if (locale!=null) {
            stats.setLocale(locale);
        }
    }
}

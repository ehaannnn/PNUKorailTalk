package com.example.korailtalk;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by ttaka on 2017. 6. 24..
 */
//db.execSQL("CREATE TABLE IF NOT EXISTS TRAIN_INFO(  boardingDate TEXT,
// departurePoint TEXT, destPoint TEXT, totalAvailableSeatNum INTEGER, trainNum INTEGER);");
public class TrainArray implements Serializable {
    BigInteger departdate;
    String departurePoint;
    String destPoint;
    int totalAvailableSeatNum;
    int trainnum;
    int nbofticket;

    TrainArray(BigInteger departdate, String departurePoint, String destPoint, int totalAvailableSeatNum, int trainnum, int nbofticket){
        this.departdate = departdate;
        this.departurePoint = departurePoint;
        this.destPoint = destPoint;
        this.totalAvailableSeatNum = totalAvailableSeatNum;
        this.trainnum = trainnum;
        this.nbofticket = nbofticket;
    }

    BigInteger getDepartdate(){
        return departdate;
    }

    String getDeparturePoint(){
        return departurePoint;
    }

    String getDestPoint(){
        return destPoint;
    }

    int getTotalAvailableSeatNum(){
        return totalAvailableSeatNum;
    }

    int getTrainnum(){
        return trainnum;
    }

    int getNbofticket(){
        return nbofticket;
    }

}

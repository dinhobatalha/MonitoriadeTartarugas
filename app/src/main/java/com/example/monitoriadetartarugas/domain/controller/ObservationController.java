package com.example.monitoriadetartarugas.domain.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.monitoriadetartarugas.domain.entitys.Observation;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObservationController {
    private Observation observation;
    private TurtleController turtleController;
    private ActivitiesController activitiesController;
    private BeachController beachController;
    private WindCategoryController windCategoryController;
    private WindDirectionController windDirectionController;
    private SQLiteDatabase connection;
    private String[] parameters;
    private StringBuilder sql;

    public ObservationController(SQLiteDatabase connection){
        this.turtleController = new TurtleController(connection);
        this.activitiesController = new ActivitiesController(connection);
        this.beachController = new BeachController(connection);
        this.windCategoryController = new WindCategoryController(connection);
        this.windDirectionController = new WindDirectionController(connection);
        this.connection = connection;
    }

    public void insert(Observation observation){
        ContentValues contentValues = new ContentValues();

        contentValues.put("idturtle", observation.getIdturtle().getIdturtle());
        contentValues.put("beach", observation.getBeach().getBeach());
        contentValues.put("dataa", observation.getDataa().toString());
        contentValues.put("beach_time", observation.getBeach_time().toString());
        contentValues.put("beach_height", observation.getBeach_height());
        contentValues.put("wc", observation.getWc().getIdwc());
        contentValues.put("wd", observation.getWd().getIdwd());

        connection.insertOrThrow("observation",null, contentValues);
    }

    public void remove(int idturtle, String beach, Date dataa){
        String[] parameters = new String[3];

        parameters[0] = String.valueOf(idturtle);
        parameters[1] = beach;
        parameters[2] = dataa.toString();

        connection.delete("observation","idturtle = ? and beach = ? and dataa = ?",parameters);
    }

    public void edit(Observation observation){
        ContentValues contentValues = new ContentValues();

        contentValues.put("idturtle", observation.getIdturtle().getIdturtle());
        contentValues.put("beach", observation.getBeach().getBeach());
        contentValues.put("dataa", observation.getDataa().toString());
        contentValues.put("beach_time", observation.getBeach_time().toString());
        contentValues.put("beach_height", observation.getBeach_height());
        contentValues.put("wc", observation.getWc().getIdwc());
        contentValues.put("wd", observation.getWd().getIdwd());

        parameters = new String[3];

        parameters[0] = String.valueOf(observation.getIdturtle());
        parameters[1] = String.valueOf(observation.getBeach());
        parameters[2] = String.valueOf(observation.getDataa());

        connection.update("observation", contentValues,"idturtle = ? and beach = ? and dataa = ?", parameters);
    }

    public List<Observation> fetchAll(){
        List<Observation> observationList = new ArrayList<>();

        sql = new StringBuilder();
        sql.append("SELECT idturtle,");
        sql.append("       beach,");
        sql.append("       dataa,");
        sql.append("       wc,");
        sql.append("       wd,");
        sql.append("       idactivity,");
        sql.append("       beach_height,");
        sql.append("       beach_time");
        sql.append("  FROM observation;");
        try {
            Cursor result = connection.rawQuery(sql.toString(), null);
            String[] strings;

            if(result.getCount() > 0){
                result.moveToFirst();

                do{
                    observation = new Observation();

                    observation.setIdturtle(turtleController.fetchOne(result.getInt(result.getColumnIndexOrThrow("idturtle"))));
                    observation.setIdactivity(activitiesController.fetchOne(result.getInt(result.getColumnIndexOrThrow("idactivity"))));
                    observation.setBeach(beachController.fetchOne(result.getString(result.getColumnIndexOrThrow("beach"))));
                    observation.setBeach_height(result.getFloat(result.getColumnIndexOrThrow("beach_height")));

                    strings = result.getString(result.getColumnIndexOrThrow("beach_time")).split(":");
                    observation.setBeach_time(new Time(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])));

                    observation.setWc(windCategoryController.fetchOne(result.getInt(result.getColumnIndexOrThrow("wc"))));
                    observation.setWd(windDirectionController.fetchOne(result.getInt(result.getColumnIndexOrThrow("wd"))));
                    observation.setDataa(new Date(result.getString(result.getColumnIndexOrThrow("dataa"))));

                    observationList.add(observation);
                }while(result.moveToNext());
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return observationList;
    }

    public Observation fetchOne(int idturtle, String beach, Date dataa){
        observation = new Observation();
        sql = new StringBuilder();

        sql.append("SELECT idturtle,");
        sql.append("       beach,");
        sql.append("       dataa,");
        sql.append("       wc,");
        sql.append("       wd,");
        sql.append("       idactivity,");
        sql.append("       beach_height,");
        sql.append("       beach_time");
        sql.append("  FROM observation");
        sql.append("  WHERE idturtle = ? and beach = ? and dataa = ?;");

        parameters = new String[3];
        parameters[0] = String.valueOf(idturtle);
        parameters[1] = beach;
        parameters[2] = String.valueOf(dataa);

        Cursor result = connection.rawQuery(sql.toString(), parameters);
        String[] strings;

        if(result.getCount() > 0){
            result.moveToFirst();

            observation.setIdturtle(turtleController.fetchOne(result.getInt(result.getColumnIndexOrThrow("idturtle"))));
            observation.setIdactivity(activitiesController.fetchOne(result.getInt(result.getColumnIndexOrThrow("idactivity"))));
            observation.setBeach(beachController.fetchOne(result.getString(result.getColumnIndexOrThrow("beach"))));
            observation.setBeach_height(result.getFloat(result.getColumnIndexOrThrow("beach_height")));

            strings = result.getString(result.getColumnIndexOrThrow("beach_time")).split(":");
            observation.setBeach_time(new Time(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2])));

            observation.setWc(windCategoryController.fetchOne(result.getInt(result.getColumnIndexOrThrow("wc"))));
            observation.setWd(windDirectionController.fetchOne(result.getInt(result.getColumnIndexOrThrow("wd"))));
            observation.setDataa(new Date(result.getString(result.getColumnIndexOrThrow("dataa"))));

            return observation;
        }

        return null;
    }
}

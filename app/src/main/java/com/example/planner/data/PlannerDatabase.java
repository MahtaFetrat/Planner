package com.example.planner.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.planner.model.*;

@Database(entities = {Task.class, DailyTask.class, Motivation.class}, version = 1)
public abstract class PlannerDatabase extends RoomDatabase {

    public abstract PlannerDao plannerDao();

    private static PlannerDatabase INSTANCE;

    public static PlannerDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PlannerDatabase.class, "DB_PLANNER")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
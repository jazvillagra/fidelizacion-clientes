package com.github.arquiweb.fidelizacion.ejb;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class TareaProgramadaDAO {

    @Schedule(minute = "*/60", hour = "*", persistent = false)
    public void atSchedule() throws InterruptedException {
        System.out.println("DeclarativeScheduler:: In atSchedule()");
    }

}
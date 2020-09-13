package com.github.arquiweb.fidelizacion.ejb;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import com.github.arquiweb.fidelizacion.model.BolsaPuntos;

@Singleton
public class TareaProgramadaDAO {

	@Inject
	private BolsaPuntosDAO bolsaPuntosDAO;
	
    @Schedule(minute = "*", hour = "*/24", persistent = false)
    public void atSchedule() throws InterruptedException {
    	List<BolsaPuntos> bolsaPuntos = bolsaPuntosDAO.listar();
    	System.out.print("Actualizacion programada de bolsa puntos");
    	if(bolsaPuntos != null && !bolsaPuntos.isEmpty()) {
    		List<BolsaPuntos> bolsasVencidas = bolsaPuntos.stream().filter(
    				bolsa -> bolsa.getFechaVencimiento().before(new Date())).collect(Collectors.toList());
    		for(BolsaPuntos bolsa : bolsasVencidas) {
    			bolsa.setSaldo(0);
    			bolsaPuntosDAO.actualizar(bolsa);
    		}
    	}
    }

}
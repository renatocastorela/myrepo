package org.oiga.estructura.manager;

import java.util.List;

import org.oiga.estructura.populators.Populator;

/**
 * El trabajo de esta clase es realizar actividades de gestion como 
 * cargar la configuracion de los repositorios, gestionar el modo de invocacion, gestion de  
 * errores, logging, entre otras cosas.
 * TODO:Exponer metodos de llamada como un WS
 * @author jaime.renato
 *
 */
public class RepositoryProcessingManager {
	
	private List<Populator> populators;
	
	public void process()
	{
		for(Populator p:populators){
			p.populate();
		}
	}

}

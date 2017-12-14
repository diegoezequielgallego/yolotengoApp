package com.yolotengo.gatewayApp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yolotengo.gatewayApp.controller.RestApiController;
import com.yolotengo.gatewayApp.dto.CountDTO;
import com.yolotengo.gatewayApp.util.DnaUtils;
import com.yolotengo.gatewayApp.util.MutantThread;
import com.yolotengo.gatewayApp.util.ThreadUtil;

import com.yolotengo.gatewayApp.dto.RowDna;
import com.yolotengo.gatewayApp.model.Mutant;
import com.yolotengo.gatewayApp.repositories.MutantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class MutantService {
	
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
    MutantRepository mutantRepository;
	
	public List<Mutant> getAllMutants(){
		return mutantRepository.findAll();
	}


	public void clearStats() {
		DnaUtils.setCount(null);
	}

	public void restart() {
		mutantRepository.deleteAll();
		DnaUtils.setCount(null);
	}


	public CountDTO getStats(){
		if(DnaUtils.getCount() == null){
			DnaUtils.setCount(mutantRepository.findTotales());
			DnaUtils.getCount().setCountHumanDna(mutantRepository.count() - DnaUtils.getCount().getCountMutantDna());
			DnaUtils.getCount().setRatio((float) DnaUtils.getCount().getCountMutantDna() / DnaUtils.getCount().getCountHumanDna());
		}
		return DnaUtils.getCount();
	}


	public boolean isMutant(List<String> dna) {

		//Uso este mapa como si fuese una memoria cache 
		//lo ideal seria usar memcache u otro cache distribuido o MongoDB
		//como es un examen y lo puedan levantar localmente sin instalar nada 
		//o levantarlo facilmente en el contenedor onlcloud decidi no usar estas tecnologias
		if(DnaUtils.getDnaMap().get(Arrays.hashCode(dna.toArray())) != null){
			logger.warn("el DNA ya está registrado");
			return DnaUtils.getDnaMap().get(Arrays.hashCode(dna.toArray()));
		}


		RowDna rowDna;
		List<RowDna> rowDnaList = new ArrayList<>();
		StringBuffer stb = new StringBuffer();
		
		// simulo la matriz en un objeto Row que dentro tiene una lista de string
		// simulando las columnas
		for (String dnaLine : dna) {
			rowDna = new RowDna();
			rowDna.setDnaLine(Arrays.asList(dnaLine.split("(?!^)")));
			rowDnaList.add(rowDna);
			stb.append(dnaLine);
			stb.append("-");
		}

		boolean mutantResult = analizeMatrix(rowDnaList);
		
		Mutant mutant = new Mutant();
		mutant.setDna(stb.toString());
		mutant.setName(DnaUtils.generateRandomName());
		mutant.setIsMutant(mutantResult ? 1 : 0);

		//Para que la api puede soportar la carga hago que el guardado en la base de datos se ejecute en
		//un thread aparte, asi la respusta es rapida y en paralelo puede ejecutar hasta 50 hilos y que los valla
		//guardando en la BBDD gradualmente
		ThreadUtil.getExecutorService().execute(new MutantThread(mutant, mutantRepository));

		DnaUtils.getDnaMap().put(Arrays.hashCode(dna.toArray()), mutantResult);
		amountRatio(mutantResult);

		return mutantResult;
	}

	private void amountRatio(Boolean mutantResult){
		if(DnaUtils.getCount() != null){
			if(mutantResult){
				DnaUtils.getCount().sumMutant();
			}else{
				DnaUtils.getCount().sumHuman();
			}
			DnaUtils.getCount().setRatio((float) DnaUtils.getCount().getCountMutantDna() / DnaUtils.getCount().getCountHumanDna());
		}

	}

	private boolean analizeMatrix(List<RowDna> rowDnaList) {
		int rowPos;
		int columPos;
		int mathDnaMutant = 0;

		//LEO LA MATRIZ DE IZQ A DERECHA Y DE ARRIBA HACIA ABAJO
		//analizo solo vericalmente de arriba hacia abajo, ya que 
		//analizo la matriz en orden si lo encuentra de abajo hacia arriba
		//ya encontro el match previamente de arriba hacia abajo
		//pasa lo mismo al analizar de manera horizontal y oblicua
		rowPos = 0;
		for (RowDna auxDna : rowDnaList) {
			columPos = 0;

			for (String nitroBase : auxDna.getDnaLine()) {

				if (mathDnaMutant == 2) {
					return true;
				}

				// Esta validacion la hago por si esta en la columna 4 de 6
				// nunca va a tener 4 campos continuos horizontales
				// hacia adelantem, por lo tanto evito procesamiento innecesario
				if ((columPos + 3) <= auxDna.getDnaLine().size()) {
					// BUSCAR DE MANERA HORIZONTAL DE IZQ A DERECHA
					if (searchHorizontal(auxDna, nitroBase, columPos, 0)) {
						logger.warn("Encontro un match con la base nitrogenada: " + nitroBase);
						mathDnaMutant++;
					}
				}

				// Esta validación la hago para estar seguro que quedan 4 items para buscar
				// de arriba hacia abajo y evitar procesamiento innecesario
				if (rowPos + 3 <= auxDna.getDnaLine().size()) {
					// BUSCAR DE MANERA VERTICAL DE ARRIBA HACIA ABAJO
					if (searchVertical(rowDnaList, nitroBase, rowPos, columPos, 0)) {
						logger.warn("Encontro un match con la base nitrogenada: " + nitroBase);
						mathDnaMutant++;
					}
				}

				// Esta validación la hago para estar seguro que quedan 4 items para buscar
				// tanto de arriba hacia abajo como de izq a derecha y evitar procesamiento
				// innecesario
				if ((columPos + 3 <= auxDna.getDnaLine().size()) && (rowPos + 3 <= auxDna.getDnaLine().size())) {
					// BUSCAR DE MANERA OBLICUA DE ARRIBA HACIA ABAJO Y DE IZQ A DERECHA
					if (searchObliquePositive(rowDnaList, nitroBase, rowPos, columPos, 0)) {
						logger.warn("Encontro un match con la base nitrogenada: " + nitroBase);
						mathDnaMutant++;
					}
				}

				// Esta validación la hago para estar seguro que quedan 4 items para buscar
				// tanto de arriba hacia abajo como de derecha a izq y evitar procesamiento
				// innecesario
				if ((columPos - 3 >= 0) && (rowPos + 3 <= auxDna.getDnaLine().size())) {
					// BUSCAR DE MANERA OBLICUA DE ARRIBA HACIA ABAJO Y DE DERECHA A IZQ
					if (searchObliqueNegative(rowDnaList, nitroBase, rowPos, columPos, 0)) {
						logger.warn("Encontro un match con la base nitrogenada: " + nitroBase);
						mathDnaMutant++;
					}
				}

				columPos++;
			}
			rowPos++;
		}
		return false;

	}

	private boolean searchHorizontal(RowDna auxDna, String nitroBase, int columPos, int matchCount) {

		if (columPos == auxDna.getDnaLine().size()) {
			return false;
		}

		if (nitroBase.equals(auxDna.getDnaLine().get(columPos++))) {
			matchCount++;
			if (matchCount == 4) {
				return true;
			}
			return searchHorizontal(auxDna, nitroBase, columPos, matchCount);
		}

		return false;
	}

	private boolean searchVertical(List<RowDna> rowDnaList, String nitroBase, int rowPos, int columPos,
			int matchCount) {

		if (rowPos == rowDnaList.size()) {
			return false;
		}

		if (nitroBase.equals(rowDnaList.get(rowPos++).getDnaLine().get(columPos))) {
			matchCount++;
			if (matchCount == 4) {
				return true;
			}
			return searchVertical(rowDnaList, nitroBase, rowPos, columPos, matchCount);
		}

		return false;
	}

	private boolean searchObliquePositive(List<RowDna> rowDnaList, String nitroBase, int rowPos, int columPos,
			int matchCount) {

		if (rowPos == rowDnaList.size()) {
			return false;
		}

		if (columPos == rowDnaList.size()) {
			return false;
		}

		if (nitroBase.equals(rowDnaList.get(rowPos++).getDnaLine().get(columPos++))) {
			matchCount++;
			if (matchCount == 4) {
				return true;
			}
			return searchObliquePositive(rowDnaList, nitroBase, rowPos, columPos, matchCount);
		}

		return false;
	}

	private boolean searchObliqueNegative(List<RowDna> rowDnaList, String nitroBase, int rowPos, int columPos,
			int matchCount) {

		if (rowPos == rowDnaList.size()) {
			return false;
		}

		if (nitroBase.equals(rowDnaList.get(rowPos++).getDnaLine().get(columPos--))) {
			matchCount++;
			if (matchCount == 4) {
				return true;
			}
			return searchObliqueNegative(rowDnaList, nitroBase, rowPos, columPos, matchCount);
		}

		return false;
	}

}

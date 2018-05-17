package backend;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ModeloTabelaArquivosSecretos extends AbstractTableModel {
	private ArrayList<String> linhas=null;
	private String[] colunas=null;
	
	
	public ModeloTabelaArquivosSecretos(ArrayList<String> lin, String[] col) {
		setLinhas(lin);
		setColunas(col);
	}
	
	
	@Override
	public int getColumnCount() {
		
		return colunas.length;
	}
	public ArrayList<?> getLinhas() {
		return linhas;
	}


	public void setLinhas(ArrayList linhas) {
		this.linhas = linhas;
	}


	public String[] getColunas() {
		return colunas;
	}


	public void setColunas(String[] colunas) {
		this.colunas = colunas;
	}


	@Override
	public int getRowCount() {
	
		return linhas.size();
	}
	@Override
	public Object getValueAt(int numLin, int numCol) {
		Object[] linha=(Object[])getLinhas().get(numLin);
		return linha[numCol];
	}
	
	public String getColumnName(int numCol) {
		return colunas[numCol];
	}
}

package T3;

import java.util.Vector;

public class Tabla {


	private String pageTitle;
	private String title;
	private String url;
	private String hasHeader;
	private String headerPosition;
	private String tableType;
	private String tableNum;
	private String s3Link;
	private String recordEndOffset;
	private String recordOffset;
	private String tableOrientation;
	private String TableContextTimeStampBeforeTable;
	private String TableContextTimeStampAfterTable;
	private String lastModified;
	private String textBeforeTable;
	private String textAfterTable;
	private String hasKeyColumn;
	private String keyColumnIndex;
	private String headerRowIndex;
	private String [][]relation;
	private int filas;
	private int columnas;
	private Vector<String> VectorHeader;
	
	public Tabla() {
		super();
		this.pageTitle = "";
		this.title = "";
		this.url = "";
		this.hasHeader = "";
		this.headerPosition = "";
		this.tableType = "";
		this.tableNum = "";
		this.s3Link = "";
		this.recordEndOffset = "";
		this.recordOffset = "";
		this.tableOrientation = "";
		this.TableContextTimeStampBeforeTable = "";
		this.lastModified = "";
		this.textBeforeTable = "";
		this.textAfterTable = "";
		this.hasKeyColumn = "";
		this.keyColumnIndex = "";
		this.headerRowIndex = "";
		this.TableContextTimeStampAfterTable="";
		this.relation = null;
		this.filas = 0;
		this.columnas = 0;
	}


	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHasHeader() {
		return hasHeader;
	}
	public void setHasHeader(String hasHeader) {
		this.hasHeader = hasHeader;
	}
	public String getHeaderPosition() {
		return headerPosition;
	}
	public void setHeaderPosition(String headerPosition) {
		this.headerPosition = headerPosition;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getTableNum() {
		return tableNum;
	}
	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}
	public String getS3Link() {
		return s3Link;
	}
	public void setS3Link(String s3Link) {
		this.s3Link = s3Link;
	}
	public String getRecordEndOffset() {
		return recordEndOffset;
	}
	public void setRecordEndOffset(String recordEndOffset) {
		this.recordEndOffset = recordEndOffset;
	}
	public String getRecordOffset() {
		return recordOffset;
	}
	public void setRecordOffset(String recordOffset) {
		this.recordOffset = recordOffset;
	}
	public String getTableOrientation() {
		return tableOrientation;
	}
	public void setTableOrientation(String tableOrientation) {
		this.tableOrientation = tableOrientation;
	}
	public String getTableContextTimeStampBeforeTable() {
		return TableContextTimeStampBeforeTable;
	}
	public void setTableContextTimeStampBeforeTable(String tableContextTimeStampBeforeTable) {
		TableContextTimeStampBeforeTable = tableContextTimeStampBeforeTable;
	}
	public String getLastModified() {
		return lastModified;
	}
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	public String getTextBeforeTable() {
		return textBeforeTable;
	}
	public void setTextBeforeTable(String textBeforeTable) {
		this.textBeforeTable = textBeforeTable;
	}
	public String getTextAfterTable() {
		return textAfterTable;
	}
	public void setTextAfterTable(String textAfterTable) {
		this.textAfterTable = textAfterTable;
	}
	public String getHasKeyColumn() {
		return hasKeyColumn;
	}
	public void setHasKeyColumn(String hasKeyColumn) {
		this.hasKeyColumn = hasKeyColumn;
	}
	public String getKeyColumnIndex() {
		return keyColumnIndex;
	}
	public void setKeyColumnIndex(String keyColumnIndex) {
		this.keyColumnIndex = keyColumnIndex;
	}
	public String getHeaderRowIndex() {
		return headerRowIndex;
	}
	public void setHeaderRowIndex(String headerRowIndex) {
		this.headerRowIndex = headerRowIndex;
	}
	public String [][] getRelation() {
		return relation;
	}
	public void setRelation(String [][] relation) {
		this.relation = relation;
	}
	public int getFilas() {
		return filas;
	}
	public void setFilas(int filas) {
		this.filas = filas;
	}
	public int getColumnas() {
		return columnas;
	}
	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}
	public String getTableContextTimeStampAfterTable() {
		return TableContextTimeStampAfterTable;
	}

	public void setTableContextTimeStampAfterTable(String tableContextTimeStampAfterTable) {
		TableContextTimeStampAfterTable = tableContextTimeStampAfterTable;
	}

	public void VectorHeader() {
		String letra;
		Boolean header = Boolean.valueOf(this.getHasHeader());
		VectorHeader=new Vector<>();
		if(header) {
			String relaciones[][]=this.getRelation();
			if(this.getTableOrientation().compareTo("VERTICAL")==0) {
				//System.out.println(T.getTableType());
				//System.out.println(T);
				for(int i=0;i<this.getColumnas();i++) {
					letra=relaciones[0][i];
					letra=letra.replaceAll("\"", "");
					letra=letra.replaceAll(":", "");
					letra=letra.replaceAll(";", "");
					letra=letra.replaceAll("\\?", "");
					letra=letra.replaceAll("\\(", "");
					letra=letra.replaceAll("\\)", "");
					//letra=letra.replaceAll(".", "");
					//letra=letra.replaceAll(",", "");
					//System.out.println(letra);
					if(letra.compareTo("")!=0) {
						VectorHeader.add(letra.toLowerCase().trim());
					}
				}
			}else if(this.getTableOrientation().compareTo("HORIZONTAL")==0) {
				//System.out.println(T);
				for(int i=0;i<this.getFilas();i++) {
					letra=relaciones[i][0];
					letra=letra.replaceAll("\"", "");
					letra=letra.replaceAll(":", "");
					letra=letra.replaceAll(";", "");
					letra=letra.replaceAll("\\?", "");
					letra=letra.replaceAll("\\(", "");
					letra=letra.replaceAll("\\)", "");
					//letra=letra.replaceAll(".", "");
					//	letra=letra.replaceAll(",", "");
					//System.out.println(letra);
					if(letra.compareTo("")!=0) {
						VectorHeader.add(letra.toLowerCase().trim());
					}
				}

			}

		}
	}
	
	@Override
	public String toString() {
		String salida = "";
		salida = "relation=\n";
		for(int i=0;i<this.getColumnas();i++) {
			for(int j=0;j<this.getFilas();j++) {
				if(j==0) salida=salida+"\t"+relation[j][i]+" ";
				else salida=salida+relation[j][i]+" ";
			}
			salida+="\n";
		}
		salida+="\n";
		salida+=  
				"pageTitle=\n\t" + pageTitle + 
				"\ntitle=\n\t" + title + 
				"\nurl=\n\t" + url + 
				"\nhasHeader=\n\t" + hasHeader + 
				"\nheaderPosition=\n\t" + headerPosition + 
				"\ntableType=\n\t" + tableType + 
				"\ntableNum=\n\t" + tableNum + 
				"\ns3Link=\n\t" + s3Link + 
				"\nrecordEndOffset=\n\t" + recordEndOffset + 
				"\nrecordOffset=\n\t" + recordOffset +
				"\ntableOrientation=\n\t" + tableOrientation + 
				"\nTableContextTimeStampBeforeTable=\n\t"+ TableContextTimeStampBeforeTable + 
				"\nTableContextTimeStampAfterTable=\n\t"+ TableContextTimeStampAfterTable + 
				"\nlastModified=\n\t" + lastModified + 
				"\ntextBeforeTable=\n\t"+ textBeforeTable + 
				"\ntextAfterTable=\n\t" + textAfterTable + 
				"\nhasKeyColumn=\n\t" + hasKeyColumn + 
				"\nkeyColumnIndex=\n\t" + keyColumnIndex + 
				"\nheaderRowIndex=\n\t" + headerRowIndex;
		return salida;
	}


	public Vector<String> getVectorHeader() {
		return VectorHeader;
	}


	public void setVectorHeader(Vector<String> vectorHeader) {
		VectorHeader = vectorHeader;
	}



}

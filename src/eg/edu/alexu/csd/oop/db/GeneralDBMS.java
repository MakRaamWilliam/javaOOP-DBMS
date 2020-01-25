package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;


public class GeneralDBMS implements Database {
  
 Query ob = new Query();
 xml xfile = new xml();
 Schema sfile=new Schema();
 String dir;
	ArrayList<Table> dbtables=new ArrayList<Table>();
	HashMap<String, Integer> tablesNames = new HashMap<String, Integer>();
	int countTables=0;
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
 
	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		File temp=new File(databaseName.toLowerCase());
        if(temp.exists()) {
        	if(dropIfExists) {
        		File files[]=temp.listFiles();
        		for(int i=0;i<files.length;i++) {
        			files[i].delete();
        		}
        		temp.delete();
        		dbtables.clear();
        		tablesNames.clear();
        		temp.mkdirs();
        	}
        }
        else {
        	temp.mkdirs();
        }
        setDir(temp.getAbsolutePath());
        return temp.getAbsolutePath();
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		query = query.toLowerCase();
		if( ob.call(query)== null ||  ! ob.call(query).equalsIgnoreCase("structure"))
			
			throw  new SQLException("Wrong query\n");
		
		
		query = query.replaceAll("\\s{2,}", " ").trim();
		String regex [] = query.split(" ");
		if(regex.length<3 )
			try {
				throw  new SQLException("Wrong query\n");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		if( regex[0].equalsIgnoreCase("create") && regex[1].equalsIgnoreCase("database")) {
			String databaseName = regex[2];
			createDatabase("sample" + System.getProperty("file.separator") + databaseName.toLowerCase(), true);
		}
		else if(regex[0].equalsIgnoreCase("create") && regex[1].equalsIgnoreCase("table")){
			String name=  regex[2].split("\\(")[0]; 
			if(tablesNames.containsKey(name)) return false;
			ArrayList<String> colum=new ArrayList<String>();
			ArrayList<String> colType=new ArrayList<String>();
			if(dir!=null) {
			
			tablesNames.put(name, countTables);  countTables++;
			String col []= query.split("\\(")[1].split(",");
			String []colname = new String[col.length]; String coltype[] = new String[col.length]; 
			for(int i=0; i<col.length;i++) {
				colname[i] =col[i].replaceAll("\\s{2,}", " ").trim().split(" ")[0];
				coltype[i] = col[i].replaceAll("\\s{2,}", " ").trim().split(" ")[1];
				coltype[i]= coltype[i].replace(")", "");
			}
			for(int i=0;i<colname.length;i++) {
				colum.add(colname[i]);
			 if(coltype[i].equalsIgnoreCase("int"))  colType.add(colname[i]);
			}
			
			Table nt= new Table(colum,colType);
			dbtables.add(nt);
			//add xml and schema files
			
			//create xml file
			xfile.createXml(getDir(), name, colum, colType);	
			sfile.createSchema(getDir(), name, colum);
			
			}
			else {   throw new SQLException("Can't create table before creating a database\n");
	  	  }
		}
	   else if(regex[0].equalsIgnoreCase("drop") && regex[1].equalsIgnoreCase("database")){
		   String databaseName = regex[2].toLowerCase(); 
		   File temp=new File("sample" + System.getProperty("file.separator") + databaseName.toLowerCase());
		   if(temp.exists()) {
			   File files[]=temp.listFiles();
			          		for(int i=0;i<files.length;i++) {
			          			files[i].delete();
			          		}
			   temp.delete();
			   setDir(null);
			   dbtables.clear();
			   tablesNames.clear();
			   return true; }
			   else {
				   try {
					   throw new Exception("Can't drop a non existing database\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
				  return false; 
			   }
		}
	   else if( regex[0].equalsIgnoreCase("drop") && regex[1].equalsIgnoreCase("table")){
		   String name = regex[2];
			if((tablesNames.containsKey(name))) {
				int ntable=tablesNames.get(name);
				dbtables.remove(ntable);
				tablesNames.remove(name);
				countTables--;
				xfile.deleteXml(getDir(), name);
				sfile.deleteXsd(getDir(), name);
		}
	 	else {try {
	 		throw new Exception("Can't drop a non existing table\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
	 return false;	}
	   }
		return true;
		
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		query = query.toLowerCase();
		if(! ob.call(query).equalsIgnoreCase("execute"))
			try {
				throw new Exception("Wrong query\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		query = query.replaceAll("\\s{2,}", " ").trim();
		Table table =null;
		int ft=0;
		String regex [] = query.split(" ");
		if(tablesNames.containsKey(regex[3])) {
			ft=1;
			int num = tablesNames.get(regex[3]);
		    table = dbtables.get(num);
		}
		if(ft==1) {
        Select s = new Select();
        s.executeQuery(query,table);
        return s.executeQuery(query,table);
		}
		else {
			return null;
		}
		
	    } 
				

	public int executeUpdateQuery(String query) throws SQLException {
		query = query.toLowerCase();
		if(! ob.call(query).equalsIgnoreCase("update"))
			try {
				throw new Exception("Wrong query\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		int result=0;
		int index=-1;
		boolean isint=false;
		boolean where=false;
		ArrayList<Integer>rows=new ArrayList<Integer>();
		ArrayList<ArrayList<String>> updated =new ArrayList<ArrayList<String>>();
		query = query.replaceAll("s{2,}", " " ).trim();
		String regex [] = query.split(" ");
		
		if(regex[0].equalsIgnoreCase("update")) {
			
		
			String name=regex[1].toLowerCase();
			if(!tablesNames.containsKey(name)) throw new SQLException("This table name is not found\n");
			else {		
				Table t=dbtables.get(tablesNames.get(name)); 	
				UpDated upd =new UpDated(query, updated, rows, t); 
				 result = upd.getinsert(getDir(),name);
				t.setTableList(upd.getupdated());
				
		} 
	} 
	
		
		//insert query
		

		if(regex[0].equalsIgnoreCase("Insert")) {
			String name=regex[2];
			if(name.contains("(")) {
				name=name.split("\\(")[0];
			}
			
			if(tablesNames.containsKey(name)) {
		 		Table t=dbtables.get(tablesNames.get(name)); 
				Inset ins= new Inset(query, updated, rows, t);
				result = ins.getinsert(getDir(),name);
				} 
			else {throw new SQLException("This table name is not found\n");}
		}
		

		//delete query
		if(regex[0].equalsIgnoreCase("delete")) {
//			int flag=0;
			String name=regex[2];
			if(name.contains("(")) {
				name=name.split("\\(")[0];
			}
			if(tablesNames.containsKey(name)) {
				index=tablesNames.get(name);
				Table t=dbtables.get(tablesNames.get(name)); 
				Delete delt = new Delete(query, updated, rows, t);
				result = delt.getdelete(getDir(),name);
					}
			else {throw new SQLException("This table name is not found\n");}
		} 
		

		return result;
	}
		
		
	
	

}

package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.ArrayList;

public class Inset {
	xml xfile=new xml();
	Schema sfile=new Schema();
	int result=0;
	int index=-1;
	boolean isint=false;
	boolean where=false;
	String query;
	ArrayList<Integer>rows;
	ArrayList<ArrayList<String>> updated;
	Table t;
	
	public Inset(String query, ArrayList<ArrayList<String>> u ,ArrayList<Integer>r, Table table ) {
		this.query=query; 
		 this.updated = new ArrayList<ArrayList<String>>(); updated=u;
		this.rows= new ArrayList<Integer>(); rows=r;
		this.t = new Table(table);
	
	}
    public int getinsert(String dir,String namet) {
		int result=0;
		String regex [] = query.split(" ");

		if(regex[0].equalsIgnoreCase("Insert")) {
			String name=regex[2];
			if(name.contains("(")) {
				name=name.split("\\(")[0];
			}
			
			
				updated.addAll(t.getTablelist());
				String temb=query;
				int i=0;
				temb=temb.replaceAll("\\s", "");
				int counter=t.getNamesCol().size();   //counting number of columns.
				String newValues=query;
				newValues=newValues.replaceAll("\\s", "");
				if(temb.contains(")V") || temb.contains(")v")) {   //columns are mentioned before values.
					
					
					String parse[]=temb.split("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)");
					temb=temb.replace(regex[0],"");
					temb=temb.replace(regex[1],"");
					temb=temb.replace(name,"");
					temb=temb.replace(parse[1],"");
					temb=temb.replaceAll("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)","");
					temb=temb.replace("(","");temb=temb.replace(")","");
					String col[]=temb.split(",");    //names of columns
					
					if(col.length!=counter) {
						return 0;
					}
					
					parse=newValues.split("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)");
					
					newValues=newValues.replace(parse[0],"");
					newValues=newValues.replaceAll("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)","");
					newValues=newValues.replace("(","");newValues=newValues.replace(")","");
					String values[]=newValues.split(",");
					if(values.length!=counter) {
						return 0;
					}
					for(i=0;i<col.length;i++) {   //check for all columns
						index=-1;
						isint=false;
						for(int l=0;l<t.getNamesCol().size();l++) {  
							if(col[i].equalsIgnoreCase(t.getNamesCol().get(l))) {
								index=l;
							}
						}
						if(index==-1) {
							return 0;
						}
						for(int l=0;l<t.getNameInt().size();l++) {
							if(col[i].equalsIgnoreCase(t.getNameInt().get(l))) {
								isint=true;
							}
						}
						
						if(isint&&values[i].contains("'")) {
							return 0;
						}
						if(!isint&&!values[i].contains("'")) {
							return 0;
						}
					}
					for(i=0;i<col.length;i++) {
						for(int l=0;l<t.getNamesCol().size();l++) {  
							if(col[i].equalsIgnoreCase(t.getNamesCol().get(l))) {
								index=l;
							}
						}
						ArrayList<String>arr=new ArrayList<String>();
						arr=updated.get(index);
						if(values[i].contains("'")) {
							values[i]=values[i].replaceAll("'","");
							arr.add(values[i]);
						}
						else {
							try {
								int tem = Integer.parseInt(values[i]);
							} catch (Exception e) {
								try {
									throw new SQLException("not valid int");
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							arr.add(values[i]);
						}						
						updated.remove(index);
						updated.add(index,arr);
						xfile.updateXml(dir, namet, index, arr);
						sfile.insertXsd(dir, name, index, arr);
					}
					
					}else {
						
						String parse[]=newValues.split("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)");
						newValues=newValues.replace(parse[0],"");
						newValues=newValues.replaceAll("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)","");
						newValues=newValues.replace("(","");newValues=newValues.replace(")","");
						String values[]=newValues.split(",");
						if(values.length!=counter) {
							return 0;
						}
						for(i=0;i<t.getNamesCol().size();i++) {   //check for types only
							isint=false;
							for(int l=0;l<t.getNameInt().size();l++) {
								if(t.getNamesCol().get(i).equalsIgnoreCase(t.getNameInt().get(l))) {
									isint=true;
								}
							}
							
							if(isint&&values[i].contains("'")) {
								return 0;
							}
							if(!isint&&!values[i].contains("'")) {
								return 0;
							}
						}
						for(i=0;i<t.getNamesCol().size();i++) {
							ArrayList<String>arr=new ArrayList<String>();
							arr=updated.get(i);
							if(values[i].contains("'")) {
								values[i]=values[i].replaceAll("'","");
								arr.add(values[i]);
							}
							else {
								arr.add(values[i]);
							}
							
							updated.remove(i);
							updated.add(i,arr);
							xfile.updateXml(dir, namet, i, arr);
							sfile.insertXsd(dir, name, i, arr);
						}
						
				}
				t.setTableList(updated);
				result=1;
			}
		
  
   return result; 	
	}
	
}

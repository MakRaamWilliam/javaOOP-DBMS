package eg.edu.alexu.csd.oop.db;

import java.util.ArrayList;

public class UpDated {
	xml xfile=new xml();
	int result=0;
	int index=-1;
	boolean isint=false;
	boolean where=false;
	String query;
	ArrayList<Integer>rows;
	ArrayList<ArrayList<String>> updated;
	Table t;
	
	public UpDated(String query, ArrayList<ArrayList<String>> u ,ArrayList<Integer>r, Table table ) {
		this.query=query; 
		 this.updated = new ArrayList<ArrayList<String>>(); this.updated=u;
		this.rows= new ArrayList<Integer>(); rows=r;
		this.t = new Table(table);
	
	}
	
    public int getinsert(String dir,String name) {
		int result=0;
		query = query.replaceAll("s{2,}", "" ).trim();
		String regex [] = query.split(" ");
		
	
				String temb=query; 
				updated.addAll(t.getTablelist());
				
				temb=temb.replace(regex[0],"");
				
				temb=temb.replace(regex[1],"");
			
				temb=temb.replace(regex[2],"");
			
				
				for(int i=3;i<regex.length;i++) {
					if(regex[i].equalsIgnoreCase("where")) {
						where=true;
						int indexOfWhere=i;
						for(int j=i;j<regex.length;j++) {  //query is deleted except columns and their new values
							temb=temb.replace(regex[j],"");
						}
						String condition=query;
						String cond[];
						for(int l=0;l<=indexOfWhere;l++) {
							condition=condition.replace(regex[l],"");
						}
						condition=condition.replaceAll("\\s","");
						
						if(condition.contains("<>")) {
							cond=condition.split("<>");
							
							for(int j=0;j<t.getNamesCol().size();j++) {  
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint &&cond[1].contains("'")) {
								return 0;
							}
							if(isint&&!cond[1].contains("'")) {
								for(int j=0;j<t.getTablelist().get(index).size();j++) {
									if(Integer.parseInt(t.getTablelist().get(index).get(j))!=Integer.parseInt(cond[1])) {
										rows.add(j);
									}
								}
							}
							else if(!isint &&!cond[1].contains("'")) {
								return 0;
							}
							else {
								cond[1]=cond[1].replaceAll("'","");
								for(int j=0;j<t.getTablelist().get(index).size();j++) {
									if(!(t.getTablelist().get(index).get(j).equalsIgnoreCase(cond[1]))) {
										rows.add(j);
									}
								}
							}
						}
						
						else if(condition.contains("<=")) {
							
							cond=condition.split("<=");
							
							if(cond[1].contains("'")) {
								return 0;
							}
							
							for(int j=0;j<t.getNamesCol().size();j++) {  
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint==false) {
								return 0;
							}
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(Integer.parseInt(t.getTablelist().get(index).get(j))<=Integer.parseInt(cond[1])) {
									rows.add(j);
								}
							}
						}
						else if(condition.contains(">=")) {
							
							cond=condition.split(">=");
							
							if(cond[1].contains("'")) {
								return 0;
							}
							
							for(int j=0;j<t.getNamesCol().size();j++) {  
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint==false) {
								return 0;
							}
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(Integer.parseInt(t.getTablelist().get(index).get(j))>=Integer.parseInt(cond[1])) {
									rows.add(j);
								}
							}
						}
						
						else if(condition.contains("=")) {
							
							cond=condition.split("=");
							
							for(int j=0;j<t.getNamesCol().size();j++) {  
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint &&cond[1].contains("'")) {
								return 0;
							}
							if(isint&&!cond[1].contains("'")) {
								for(int j=0;j<t.getTablelist().get(index).size();j++) {
									if(Integer.parseInt(t.getTablelist().get(index).get(j))==Integer.parseInt(cond[1])) {
										rows.add(j);
									}
								}
							}
							else if(!isint &&!cond[1].contains("'")) {
								return 0;
							}
							else {
								cond[1]=cond[1].replaceAll("'","");
								for(int j=0;j<t.getTablelist().get(index).size();j++) {
									if(t.getTablelist().get(index).get(j).equalsIgnoreCase(cond[1])) {
										rows.add(j);
									}
								}
							}
						}
						else if(condition.contains("<")) {
							
							cond=condition.split("<");
							if(cond[1].contains("'")) {   //check if target value is string
								return 0;
							}
							
							for(int j=0;j<t.getNamesCol().size();j++) {  //check the column name
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint==false) {
								return 0;
							}
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(Integer.parseInt(t.getTablelist().get(index).get(j))<Integer.parseInt(cond[1])) {
									rows.add(j);
								}
							}
							
	
						}
						else if(condition.contains(">")) {
							
							cond=condition.split(">");
							if(cond[1].contains("'")) {
								return 0;
							}
							
							for(int j=0;j<t.getNamesCol().size();j++) {  
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint==false) {
								return 0;
							}
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(Integer.parseInt(t.getTablelist().get(index).get(j))>Integer.parseInt(cond[1])) {
									rows.add(j);
								}
							}
						}
						
						
						
						
						
						temb=temb.replaceAll("\\s","");   //removing all spaces
						if(temb.contains(",")) {     //we have more than 1 column to be updated
							String splitOrder[]=temb.split(",");  //[column=value,column=value,.....]
							for(int j=0;j<splitOrder.length;j++) {
								isint=false;
								ArrayList<String>arr=new ArrayList<String>();
								String split[]=splitOrder[j].split("=");
								index=-1;
								for(int l=0;l<t.getNamesCol().size();l++) {  
									if(split[0].equalsIgnoreCase(t.getNamesCol().get(l))) {
										index=l;
									}
								}
								if(index==-1) {
									return 0;
								}
								for(int k=0;k<updated.get(index).size();k++) {
									if(rows.contains(k)) {
										arr.add(split[1]);
									}
									else {
										arr.add(updated.get(index).get(k));
									}
								}
								for(int l=0;l<t.getNameInt().size();l++) {
									if(split[0].equalsIgnoreCase(t.getNameInt().get(l))) {
										isint=true;
									}
								}
								if(isint&&split[1].contains("'")) {
									return 0;
								}
								if(!isint&&!split[1].contains("'")) {
									return 0;
								}
								if(isint&&!split[1].contains("'")) {
									result=rows.size();
									updated.remove(index);
									updated.add(index, arr);
									xfile.updateXml(dir, name, index, arr);
								}
								if(!t.getNameInt().contains(split[0])&&split[1].contains("'")) {
									result=rows.size();
									updated.remove(index);
									updated.add(index, arr);
									xfile.updateXml(dir, name, index, arr);
							}
						//		arr.clear();
						}
						}
						else {
							String split[]=temb.split("=");   // [column name,new value]
							ArrayList<String>arr=new ArrayList<String>();
							index=-1;
							isint=false;
							for(int l=0;l<t.getNamesCol().size();l++) {  
								if(split[0].equalsIgnoreCase(t.getNamesCol().get(l))) {
									index=l;
								}
							}
							if(index==-1) {
								return 0;
							}
							for(int k=0;k<updated.get(index).size();k++) {
								if(rows.contains(k)) {
									arr.add(split[1]);
								}
								else {
									arr.add(updated.get(index).get(k));
								}
							}
							for(int l=0;l<t.getNameInt().size();l++) {
								if(split[0].equalsIgnoreCase(t.getNameInt().get(l))) {
									isint=true;
								}
							}
							
							if(isint&&split[1].contains("'")) {
								return 0;
							}
							if(!isint&&!split[1].contains("'")) {
								return 0;
							}
							if(isint&&!split[1].contains("'")) {
								result=rows.size();
								updated.remove(index);
								updated.add(index, arr);
								xfile.updateXml(dir, name, index, arr);
							}
							if(!isint&&split[1].contains("'")) {
								result=rows.size();
								updated.remove(index);
								updated.add(index, arr);
								xfile.updateXml(dir, name, index, arr);
							}
							
						}
						i=regex.length+1;
					}
				}
				if(where==false) {
					temb=temb.replaceAll("\\s",""); 
					
					if(temb.contains(",")) {     
						String splitOrder[]=temb.split(","); 
						for(int j=0;j<splitOrder.length;j++) {
							ArrayList<String>arr=new ArrayList<String>();
							String split[]=splitOrder[j].split("=");
							index=-1;
							isint=false;
							for(int l=0;l<t.getNamesCol().size();l++) {  
								if(split[0].equalsIgnoreCase(t.getNamesCol().get(l))) {
									index=l;
								}
							}
							if(index==-1) {
								return 0;
							}
							
							for(int k=0;k<updated.get(index).size();k++) {
								arr.add(split[1]);
							}
							
							for(int l=0;l<t.getNameInt().size();l++) {
								if(split[0].equalsIgnoreCase(t.getNameInt().get(l))) {
									isint=true;
								}
							}
							if(isint&&split[1].contains("'")) {
								return 0;
							}
							
							if(!isint&&!split[1].contains("'")) {
								return 0;
							}
							
							if(isint&&!split[1].contains("'")) {
								updated.remove(index);
								updated.add(index, arr);
								xfile.updateXml(dir, name, index, arr);
								result=updated.get(index).size();
							}
							
							if(!isint&&split[1].contains("'")) {
								updated.remove(index);
								updated.add(index, arr);
								xfile.updateXml(dir, name, index, arr);
								result=updated.get(index).size();
							}
						}
					}
					else {
						String split[]=temb.split("=");   // [column name,new value]
						ArrayList<String>arr=new ArrayList<String>();
						
						index=-1;
						isint=false;
						for(int l=0;l<t.getNamesCol().size();l++) {  
							if(split[0].equalsIgnoreCase(t.getNamesCol().get(l))) {
								index=l;
							}
						}
						if(index==-1) {
							return 0;
						}
						for(int k=0;k<updated.get(index).size();k++) {
							arr.add(split[1]);
							
						}
						for(int l=0;l<t.getNameInt().size();l++) {
							if(split[0].equalsIgnoreCase(t.getNameInt().get(l))) {
								isint=true;
							}
						}
						
						if(isint&&split[1].contains("'")) {
							return 0;
						}
						
						if(!isint&&!split[1].contains("'")) {
							return 0;
						}
						
						if(isint&&!split[1].contains("'")) {
							updated.remove(index);
							updated.add(index, arr);
							xfile.updateXml(dir, name, index, arr);
							result=updated.get(index).size();
						}
						
						if(!isint&&split[1].contains("'")) {
							updated.remove(index);
							updated.add(index, arr);
							xfile.updateXml(dir, name, index, arr);
							result=updated.get(index).size();
						}
						
					}
				} 
				t.setTableList(updated);
		
   return result; 	
	}
    public ArrayList<ArrayList<String>> getupdated() {
		return this.updated;
	}
}

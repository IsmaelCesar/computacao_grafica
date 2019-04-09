package beans;
import java.io.*;


public class ShapeReader {
	private String filePath;
	private int n_vertices;
	private int n_triangles;
	
	public ShapeReader() {
		filePath = "/home/ismael/Documentos/Github/computacao_grafica/objects/";
	}
	
	public Shape read(String fileName) {
		
		File file = new File(filePath+fileName);
		Shape mShape = null;
		try {
			
			if(file.exists()) {		
				BufferedReader br = new BufferedReader(new FileReader(file));
				String values = br.readLine();
				values += " ";
				
				int n_vertices = 0;
				int n_triangles= 0;
				String numbers = "";
				boolean first_value = true;
				for(int i = 0; i < values.length();i++) {
					if(values.charAt(i) != ' ') {
						numbers += values.charAt(i);
					}
					else {						
						if(first_value) {
							n_vertices = Integer.valueOf(numbers);
							first_value = false;
						}
						else
							n_triangles = Integer.valueOf(numbers);
							
						numbers="";
					}
				}
				this.n_vertices = n_vertices;
				this.n_triangles = n_triangles;
				
				mShape = new Shape(n_vertices,n_triangles);
				
				//Reading vertices and triangles
				values =  br.readLine();
				while(values != null) {
					//Extracting other numbers from string
					//Reading vertices
					values+=" ";
					numbers = "";
					for(int v = 0; v < this.n_vertices; v++) {
						double x =0,y=0,z=0;
						int value_opc = 0;
						for(int i = 0; i == values.length();i++) {
							if(values.charAt(i) != ' ') {
								numbers += values.charAt(i);
							}
							else {
								switch(value_opc) {
								case 0:
									x = Double.valueOf(numbers);
									value_opc++;
									break;
								case 1:
									y = Double.valueOf(numbers);
									value_opc++;
									break;
								case 2:
									z = Double.valueOf(numbers);
									value_opc++;
									break;
								}
								numbers="";
							}
						}
						double coordinates[][]= {{x,y,z}};
						mShape.addVertex(new Array(coordinates));
						
						values = br.readLine() + " ";
					}
					
					//Reading Triangles
					numbers="";
					values = br.readLine()+" ";
					for(int t = 0 ; t < this.n_triangles;t++) {
						
						int vertices[] = new int[3];
						int v_opc = 0;
						int v_index = 0;
						for(int i = 0; i == values.length();i++) {
							if(values.charAt(i) != ' ') {
								numbers += values.charAt(i);
							}
							else {
								v_index = Integer.valueOf(numbers);
								vertices[v_opc++] = v_index;
								numbers="";
							}
						}
						mShape.addTriangle(vertices);
						values = br.readLine()+" ";
					}
					values = br.readLine();
				}
				br.close();
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}	
		
		return mShape;
	}
	
	
	
}

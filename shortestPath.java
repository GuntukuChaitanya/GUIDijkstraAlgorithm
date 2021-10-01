import java.util.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("dijkstra")
public class shortestPath extends JFrame {
 Panel guiPanel=new Panel();
 JFrame frame=new JFrame();
 Help help;
 Edge edge=new Edge();
 
// Creating Vertices List and Edges List
 EdgeList<Edge> edgeList=new EdgeList();
 vertexList<Vertex> vertexList=new vertexList();
 
 // Mouse Listeners
 clickListener cl=new clickListener();
 clickListener2 cl2=new clickListener2();
 clickListener3 cl3=new clickListener3();
 clickListener4 cl4=new clickListener4();
 clickListener5 cl5=new clickListener5();
 
 // Buttons
 JRadioButton rbtnAddVertex =new JRadioButton("Add Vertex");
 JRadioButton rbtnAddEdge=new JRadioButton("Add Edges");
 JRadioButton moveVertexbtn =new JRadioButton("Move Vertex");
 JRadioButton findShortPathbtn=new JRadioButton("Shortest Path");
 JRadioButton changeWeightbtn =new JRadioButton("Change a Weight to:");
 JButton addAllEdgesbtn=new JButton("Add All Edges");
 JButton setRandWeightbtn=new JButton("Random Weight");
 JButton helpbtn=new JButton("Help");
 JButton MSpanTreebtn=new JButton("Minimal Spanning Tree");
 
 JTextArea jWeight=new JTextArea(5,20);
 
 JLabel label=new JLabel();
 
 //Store Vertices to Calculate Shortest Path
 Vertex vertex1;
 Vertex vertex2;
 
 //Constructor 
 public shortestPath() {
	 super("GUI for Dijkstra Algorithm");
	 setFrame();
 }
 public void setFrame() {
	 this.setSize(700, 700);
	 this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	 this.getContentPane().setLayout(null); 
	 this.setBackground(Color.WHITE);
	 this.addPanel(); 
	 this.addBtns();
	 this.setJLabel();
	 this.setVisible(true); 
 }
 public void paint(Graphics g) {
	 super.paint(g);
	 //	**draws the vertex**
	 if(vertexList.size()>0) 
        for(int i=0;i<vertexList.size();i++) vertexList.get(i).draw(g);
	 
	 //	**draws the edges**
	 if(edgeList.size()>0) 
        for(int i=0;i<edgeList.size();i++) edgeList.get(i).draw(g);
 }
 public void addPanel() {
	 guiPanel.setBackground(Color.WHITE);
	 guiPanel.setBounds(230, 10, 440, 600);
	 guiPanel.setVisible(true);
	 this.add(guiPanel);
 }
 
 public void addBtns() {
	 
	 //Adding All Edgdes Btn 
	 addAllEdgesbtn.setBounds(10, 400, 200, 50);
	 this.add(addAllEdgesbtn);
	 addAllEdgesbtn.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 edgeDfltColor();
			 addAllEdges();
			 rmvMouseEvents();
			 setLabel(Color.GREEN,"Edges Added successfully.");
		 }
	 });
	 
	 //Setting a Random Weight Btn
	 setRandWeightbtn.setBounds(10, 450, 200, 50);
	 this.add(setRandWeightbtn);
	 setRandWeightbtn.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 rmvMouseEvents();
			 edgeDfltColor();
			 for(int i=0;i<edgeList.size();i++) {
				 setLabel(Color.GREEN,"Random Weight added to the Edges.");
				 Random rWeight=new Random();
				 int j=rWeight.nextInt(200)+1;
				 edgeList.get(i).setWeight(j);
				 repaint();
			 }
		 }
	 });
	 //Help on clicking helpbtn
	 helpbtn.setBounds(10, 500, 200, 50);
	 this.add(helpbtn);
	 helpbtn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 rmvMouseEvents();
			 help=new Help();
		}
	 });
	 //Get Minimal Spanning Tree Button
	 MSpanTreebtn.setBounds(10, 550, 200, 50);
	 this.add(MSpanTreebtn);
	 MSpanTreebtn.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			  rmvMouseEvents();
			 
			 vertexDfltColor();
			 Dijkstra dj=new Dijkstra(); 
				dj.exe(vertexList.get(1));
				dj.getMinimalTree();
			
			setLabel(Color.GREEN,"Minimal path found.");
		 }
	 });
		 jWeight.setBounds(170, 318, 40, 16);
	     this.add(jWeight);
	          
	 //	Enable Adding Vertices
	 rbtnAddVertex.setBounds(10, 100, 200, 50);
	 this.add(rbtnAddVertex);
	 
	 //Enable Adding Edges
	 rbtnAddEdge.setBounds(10,150,200,50);
	 this.add(rbtnAddEdge);
	 
	 //Enable Move Vertex
	 moveVertexbtn.setBounds(10, 200, 200, 50);
	 this.add(moveVertexbtn);
	 
	 findShortPathbtn.setBounds(10, 250, 200, 50);
	 this.add(findShortPathbtn);
	 
	 changeWeightbtn.setBounds(10, 300, 160, 50);
	 this.add(changeWeightbtn);
	 
	 ButtonGroup allRadioBtns=new ButtonGroup();
	 allRadioBtns.add(rbtnAddEdge);
	 allRadioBtns.add(rbtnAddVertex);
	 allRadioBtns.add(findShortPathbtn);
	 allRadioBtns.add(moveVertexbtn);
	 allRadioBtns.add(changeWeightbtn);
	 
     
	 //Only Enable Add Vertex Btn
	 rbtnAddVertex.addActionListener(new ActionListener() {
        @Override	
        public void actionPerformed(ActionEvent e) {
           setLabel(Color.RED," Click to Add a Vertex ");
           guiPanel.removeMouseListener(cl2);
           guiPanel.removeMouseListener(cl3);
           guiPanel.removeMouseListener(cl4);
           guiPanel.removeMouseListener(cl5);
           guiPanel.addMouseListener(cl);
           }
        });
    
    //Only Enable Add Edge Btn
    rbtnAddEdge.addActionListener(new ActionListener() {
       @Override
        public void actionPerformed(ActionEvent e) {
           setLabel(Color.RED,"click on a vertex.");
           
           guiPanel.removeMouseListener(cl);
           guiPanel.removeMouseListener(cl3);
           guiPanel.removeMouseListener(cl4);
           guiPanel.removeMouseListener(cl5);
           guiPanel.addMouseListener(cl2);
        }
    });

     //Only Enable Move Vertex Btn  
	 moveVertexbtn.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			setLabel(Color.RED," Select the desired Edge and Drag to move it ");
			guiPanel.removeMouseListener(cl);
			guiPanel.removeMouseListener(cl2);
			guiPanel.removeMouseListener(cl4);
			guiPanel.removeMouseListener(cl5);
			guiPanel.addMouseListener(cl3);
		}
	 });
	 
	 //Changing Weight of Desired Edge
	 changeWeightbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setLabel(Color.RED," Enter Weight and select a desired Edge to change ");
				guiPanel.removeMouseListener(cl);
				guiPanel.removeMouseListener(cl2);
				guiPanel.removeMouseListener(cl3);
				guiPanel.removeMouseListener(cl5);
				guiPanel.addMouseListener(cl4);
			}
		 });
	 
	 //GET Shortest Path
	 findShortPathbtn.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
			 edgeDfltColor();
			 setLabel(Color.RED," Select Start and End Vertices ");
			 
			 vertex1=new Vertex();
			 vertex2=new Vertex();
			 
			 guiPanel.removeMouseListener(cl);
			 guiPanel.removeMouseListener(cl2);
			 guiPanel.removeMouseListener(cl3);
			 guiPanel.removeMouseListener(cl4);
			 guiPanel.addMouseListener(cl5);
 
		 }
	 });
    }
 
 	//Setting-Up the JLabel
 	public void setJLabel() {
 		label.setBounds(350,670,300,30);
 		label.setForeground(Color.RED);
 		guiPanel.add(label);
 	}
 	public void setLabel(Color c,String message){ 
 		label.setForeground(c);
 		label.setText(message);
 	}

 	//Reverse Vertex Color to red
 	public void vertexDfltColor() {
		for(int i=0;i<vertexList.size();i++) {
			vertexList.get(i).setColor(Color.RED);
		}
 	}
 	
 	//Reverse Egde Color to Blue
 	public void edgeDfltColor() {
 		for(int i=0;i<edgeList.size();i++) {
 			edgeList.get(i).setColor(Color.BLUE);
 		}
 	}
 	
 
 	
 	//Adding All Edges Function
 	public void addAllEdges() {
 		edgeList=new EdgeList();
 		int n=vertexList.size();
 		for(int i=0;i<n;i++) {
 			for(int j=i+1;j<n;j++) {
 				edgeList.add(new Edge(vertexList.get(i),vertexList.get(j)));
 				edgeList.get(i).calcWeight();
 				repaint();
 			}
 		}
 	}
 	
 	//Remove Mouse Events from GUI Panel
 	public void rmvMouseEvents() {
 		 guiPanel.removeMouseListener(cl);
		 guiPanel.removeMouseListener(cl2);
		 guiPanel.removeMouseListener(cl3);
		 guiPanel.removeMouseListener(cl4);
		 guiPanel.removeMouseListener(cl5);
 	}
 	
	public static void main(String[] args) {
        shortestPath g=new shortestPath();
	}

	
	// Mouse Events for creating and adding vertices to vertexList
	protected class clickListener extends MouseAdapter{
		clickListener(){
			super();
		}
		public void mouseClicked(MouseEvent e) {
			Vertex v=new Vertex();
			int x=e.getX()+225;
			int y=e.getY()+25;
			v.setColor(Color.RED);
			v.setX(x);;
			v.setY(y);
			vertexList.add(v);
			repaint();
		}
	 }

    //Mouse Events for creating and adding edges to edgeList
	protected class clickListener2 extends MouseAdapter{
		 int i=0;
		clickListener2(){
			super();
		}
		public void mouseClicked(MouseEvent e) {
			Vertex vs=new Vertex();
			int x=e.getX()+225;
			int y=e.getY()+25;
			
			//***Check if user clicked on specific vertices***
			for(i=0;i<vertexList.size();i++) {
				int z1=vertexList.get(i).getX(),z2=vertexList.get(i).getY();
				
				if(((x<=z1+10)&&(x>=z1-10))&&((y<=z2+10)&&(y>=z2-10))) {
					vertexList.get(i).setColor(Color.GREEN);
					setLabel(Color.RED," Select Another Vertex ");
					repaint();
					if(edge.hasA()) {
						vs.setX(z1);//change back to z1,z2
						vs.setY(z2);
						edge.setB(vs);	
						break;
					}
					else {
						vs.setX(z1);
						vs.setY(z2);
						edge.setA(vs);	
						break;
					}
					}
				}
			
			//***Check if there is two point and if there is draw line form point A to point B***
		    if(edge.hasA()&&edge.hasB()) {
			    edge.calcWeight();
			    edgeList.add(edge);	
			    setLabel(Color.GREEN,"Added Edge");
			    edge=new Edge();
			    vertexDfltColor();//change color back to red
			    repaint();
    	    }
		}
	}
	
	//Mouse Event to Move a Vertex
	protected class clickListener3 extends MouseAdapter{
		
		int x,y,i=0;
		int a,b,j;// this is the point from the vertex
		boolean pressed;
		
		 clickListener3() {
			super();
		}
		 
		 public void mousePressed(MouseEvent e) {
			x=e.getX()+225;
			y=e.getY()+25;
			for(this.i=0;this.i<vertexList.size();this.i++) {
				 a=vertexList.get(this.i).getX();
				 b=vertexList.get(this.i).getY();
				if(((x<=a+10)&&(x>=a-10))&&((y<=b+10)&&(y>=b-10))) {
					pressed=true;
					vertexList.get(i).setColor(Color.GREEN);
					
					setLabel(Color.RED," Move the Vertex to your desired location ");
					repaint();
					break;
				}
			}
		 }
		 
		 public void mouseReleased(MouseEvent e) {
			if(pressed) {
				x=e.getX()+225;
				y=e.getY()+25;
				vertexList.get(this.i).setX(x);
				vertexList.get(this.i).setY(y);
				
				setLabel(Color.GREEN,"Vertex Moved.");
				this.moveEdge();
				vertexDfltColor();
				repaint();
			}
			else {
				setLabel(Color.RED," Moving Vertex Unsuccessful. ");
			}
			 
		 }
		 //Move Edge Also
		 public void moveEdge() {
			 if(edgeList.size()>=i) {
				for(j=0;j<edgeList.size();j++) {
					if(edgeList.get(j).equalsEdge(a+5, b+5)) {
						if(edgeList.get(j).equalsA(a+5, b+5)) edgeList.get(j).setA(new Vertex(x,y));
						else edgeList.get(j).setB(new Vertex(x,y));
						}
					}
				}
			}
		}
	
	//Mouse Event To Change Weight of the Edge
	protected class clickListener4 extends MouseAdapter{
		int x,y,i=0;
		public clickListener4(){
			super();
		}
		public void mouseClicked(MouseEvent e) {
			x=e.getX()+225;
			y=e.getY()+25;
			int newWeight=0;
			if(edgeList.size()>0) {
				
				try {
					newWeight=Integer.parseInt(jWeight.getText());
				}
				catch(NumberFormatException nfe) {
					setLabel(Color.RED,nfe.getMessage());
				}
				
			    for(i=0;i<edgeList.size();i++) {
				    if(edgeList.get(i).checkEdge(x, y)&&newWeight!=0){
					    edgeList.get(i).setWeight(newWeight);
					    setLabel(Color.GREEN,"New Weight Added");
					    repaint();
					    break;
				    }
				    else setLabel(Color.RED," Weight Not Added. ");
			    }	
		    }
		    repaint();
	    }	
	}
	
	//Mouse Event to GET Shortest Path
	protected class clickListener5 extends MouseAdapter
	{
		public clickListener5() {
			super();
		}
		
		public void mouseClicked(MouseEvent e) {
			edgeDfltColor();
			int x=e.getX()+225;
			int y=e.getY()+25;
			int i;
			vertex2=new Vertex();
			for(i=0;i<vertexList.size();i++) {
				int z1=vertexList.get(i).getX(),z2=vertexList.get(i).getY();
				
				if(((x<=z1+10)&&(x>=z1-10))&&((y<=z2+10)&&(y>=z2-10))) {
					if(vertex1.isEmpty()) {
						vertex1=vertexList.get(i);
						vertexList.get(i).setColor(Color.GREEN);
						setLabel(Color.RED,"Click on Destination Vertex.");
					}
					else if(vertex2.isEmpty()) 
						{vertex2=vertexList.get(i);
						vertexList.get(i).setColor(Color.GREEN);
						}
					repaint();
					break;
				}
			}
			
			//Find Shortest Path Using Dijkstra Algorithm
			if(!vertex1.isEmpty()&&!vertex2.isEmpty()){
				Dijkstra dj=new Dijkstra();
				dj.exe(vertex1);
				dj.getShortPath(vertex1,vertex2);
			}
		}
	}
	
	//Dijkstra's Algorithm
	protected class Dijkstra{
		private  ArrayList<Vertex> vertices;
		private EdgeList<Edge> edges;
		private Set<Vertex> visitedVertices;
		private Set<Vertex> unvisitedVertices;
		private Map<Vertex, Integer> totalWeight;
		private Map<Vertex,Vertex> prevVertices;
		
		public Dijkstra() {
			vertices=new ArrayList<>();
			for(int i=0;i<vertexList.size();i++) {
				Vertex v=new Vertex(vertexList.get(i).getX(),vertexList.get(i).getY());
				this.vertices.add(v);
			}
			this.edges=edgeList;
		}
		
		public void exe(Vertex start) {
			visitedVertices=new HashSet<>();
			unvisitedVertices=new HashSet<>();
			totalWeight=new HashMap<>();
			prevVertices=new HashMap<>();
			this.totalWeight.put(start, 0);
			unvisitedVertices.add(start);
			while(unvisitedVertices.size()>0) {
				Vertex vertex=getMinimum(unvisitedVertices);
				visitedVertices.add(vertex);
				unvisitedVertices.remove(vertex);
				findMinimalWeights(vertex);
			}
		}

		private Vertex getMinimum(Set<Vertex> vs) {
			Vertex min=null;
			for(Vertex v:vs) {
				if(min==null) min=v;
				else{
					if(this.getShortestDistance(v)<this.getShortestDistance(min)) min=v;
				}
			}
			return min;
		}

		public void findMinimalWeights(Vertex vertex) {
			vertexList<Vertex> adjVertices=getNeighbours(vertex);
			for(int i=0;i<adjVertices.size();i++) {
				Vertex target=adjVertices.get(i);
				if(getShortestDistance(target)>getShortestDistance(vertex)+getDistance(vertex,target)) {
					totalWeight.put(target, getShortestDistance(vertex)+getDistance(vertex,target));
					prevVertices.put(target, vertex);
					unvisitedVertices.add(target);
				}
			}
		}

		private int getDistance(Vertex vertex, Vertex target)  {
			int weight=0;
			Edge edge=new Edge(vertex,target);
			int index=this.getIndexOf(edge);
			if(index!=-1) weight=edges.get(index).getWeight();
			else throw new RuntimeException("no such edge");
			return weight;
		}

		private int getShortestDistance(Vertex vertex) {
			Integer d=totalWeight.get(vertex);
			if(d==null) return Integer.MAX_VALUE;
			else return d;
		}

		private vertexList<Vertex> getNeighbours(Vertex vertex) {
			vertexList<Vertex> temp=new vertexList<>();
			for(int i=0;i<vertices.size();i++) {
				if(!visitedVertices.contains(vertices.get(i))) {
					if(this.checkNeighbour(new Edge(vertex,vertices.get(i)))==true){
					temp.add(vertices.get(i));}
				}}
			return temp;
		}

		public Map<Vertex, Vertex> getPrev(){
			return this.prevVertices;
		}
		public boolean checkNeighbour(Edge e) {
			int x1,x2,y1,y2,a,b,c,d;//a=x1 b=x2 c=y1 d=y2 where abcd are point of edge from edgelist
			boolean check=false;
			x1=e.getPointA().getX();x2=e.getPointB().getX();
			y1=e.getPointA().getY();y2=e.getPointB().getY();
			for(int i=0;i<edges.size();i++) {
				a=edges.get(i).getPointA().getX();b=edges.get(i).getPointB().getX();
				c=edges.get(i).getPointA().getY();d=edges.get(i).getPointB().getY();
				if(a==x1&&b==x2&&c==y1&&d==y2) {
					check= true;
					break;
				}if(a==x2&&b==x1&&c==y2&&d==y1) {
					check= true;
					break;
				}}
			return check;
		}

		public int getIndexOf(Edge e) {
			int x1,x2,y1,y2,a,b,c,d,index=-1;//a=x1 b=x2 c=y1 d=y2 where abcd are point of edge from edgelist
			x1=e.getPointA().getX();
			x2=e.getPointB().getX();
			y1=e.getPointA().getY();
			y2=e.getPointB().getY();
			for(int i=0;i<edges.size();i++) {
				a=edges.get(i).getPointA().getX();
				b=edges.get(i).getPointB().getX();
				c=edges.get(i).getPointA().getY();
				d=edges.get(i).getPointB().getY();
				if(a==x1&&b==x2&&c==y1&&d==y2) {
					index=i;
					break;
				}
				if(a==x2&&b==x1&&c==y2&&d==y1) {
					index= i;
					break;
				}
			}
			return index;
		}
		 
		public void getMinimalTree() {
			int i=0;
			ArrayList<Vertex> target=new ArrayList<>(prevVertices.keySet());;
			ArrayList<Vertex> previous=new ArrayList<>(prevVertices.values()) ;
			if(target.size()==previous.size()) {
			for(int j=0;j<prevVertices.size();j++) {
				i=this.getIndexOf(new Edge(target.get(j),previous.get(j)));
				edgeList.get(i).setColor(Color.GREEN);
			}
			
			}
			else throw new RuntimeException("Error(Getting Minimum), target.size and previous.size do not match");
			repaint();
			vertexDfltColor();
			
		}
		
		public void getShortPath( Vertex a,Vertex b) {
			vertexDfltColor();
			if(!b.isEmpty()) {
				int x,y;
				ArrayList<Vertex> keys=new ArrayList<>(prevVertices.keySet());
				LinkedList<Vertex> path=new LinkedList<>();
				Vertex start=b;
				for(int i=0;i<keys.size();i++) {
					x=keys.get(i).getX();y=keys.get(i).getY();
					if(x==start.getX()&&y==start.getY()) {
						start=keys.get(i);
						break;
				    }}
				if(prevVertices.get(start)==null) return;
				path.add(start);
			    while(prevVertices.get(start)!=null) {
				    path.add(prevVertices.get(start));
				    start=prevVertices.get(start);
				}
			    Collections.reverse(path);
			    int i;
			    for(i=0;i<path.size();i++) {
				    try {
				        int index=this.getIndexOf(new Edge(path.get(i),path.get(i+1)));
				        edgeList.get(index).setColor(Color.RED);
					}catch(IndexOutOfBoundsException e){
					    setLabel(Color.RED,"ERROR"+e.getMessage());
				    }}
				repaint();
				setLabel(Color.GREEN,"Shortest Path Routed ");
			}
		}}//End of Dijsktra
	
	//vertexList Class 
	protected class vertexList<Vertex> {
		private ArrayList<Vertex> vertexList;
		public vertexList() {
			vertexList=new ArrayList<>();
		}
		public void add(Vertex d) {
			vertexList.add(d);
		}
		public int size() {
			return vertexList.size();
		}
		public Vertex get(int i){
			return vertexList.get(i);
		}
		public ArrayList<Vertex> getList(){
			return vertexList;
		}
		public Iterator<Vertex> iterator() {
			ArrayList<Vertex> list=new ArrayList<>();
			for(int i=0;i<vertexList.size();i++) {
				list.add(vertexList.get(i));
			}
			return list.iterator();
		}
		}

	//Vertex Class
	protected class Vertex {
		private int x;
		private int y;
		private Color color;

		public Vertex() {
			this.color=color.RED;
			x=y=0;
		}
		public Vertex(int a, int b) {
			this.color=color.RED;
			x=a;
			y=b;
		}
		public void setX(int a) {
			x=a;
		}
		public void setY(int b) {
			y=b;
		}
		public void setColor(Color c) {
			this.color=c;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public boolean isEmpty() {
			if(this.x==0&&this.y==0)
				return true;
			else return false;
		}
		public String getColor() {
			return color.toString();
		}
		public void draw(Graphics g) {
			g.setColor(color);
			g.fillOval(x, y, 10, 10);
			
		}
	}
	
	//Edge Class
	protected class Edge {
		private int x1,y1,x2,y2;
		private int weight;
		private double slope;
		Color color=Color.BLUE;
		Vertex v;

		public Edge() {
			x1=x2=y1=y2=0;
			weight=0;
			v=new Vertex();
		}
		public Edge(Vertex a,Vertex b) {
			x1=a.getX()+5;
			x2=b.getX()+5;
			y1=a.getY()+5;
			y2=b.getY()+5;
			this.calcWeight();
		}

		public void setEdge(Vertex a,Vertex b) {
			x1=a.getX();
			y1=a.getY();
			x2=b.getX();
			y2=b.getY();
		}

		public void setA(Vertex a) {
			x1=a.getX()+5;
			y1=a.getY()+5;
		}

		public void setB(Vertex b) {
			x2=b.getX()+5;
			y2=b.getY()+5;
		}

		public void setColor(Color c) {
			this.color=c;
		}
		public int calcWeight() {	weight=(int)Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
			return weight;
		}
		public int getWeight() {
		return weight;	
		}

		public void setWeight(int weight) {
			this.weight=weight;
		}

		public void draw(Graphics g) {
			int midPointX=(int)((x2+x1)/2);
			int midPointY=(int)((y2+y1)/2);
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(weight), midPointX, midPointY);
			g.setColor(color);
			g.drawLine(x1, y1, x2, y2);
		}
		public Vertex getPointA() {
			Vertex A=new Vertex(x1,y1);
			return A;
		}
		public Vertex getPointB() {
		Vertex B=new Vertex(x2,y2);
		return B;
		}
		public boolean hasA() {
			return (x1>0&&y1>0);	
		}
		public boolean hasB() {
			return (x2>0&&y2>0);	
		}
		public boolean equalsA(int x,int y) {
			return (x==x1&&y==y1);
		}
		public boolean equalsB(int x,int y) {
			return (x==x2&&y==y2);
		}
		public boolean equalsEdge(int x,int y) {
			return(this.equalsA(x, y)||this.equalsB(x, y));
		}

		public double calcSlope() {
			return slope=(double)(y2-y1)/(x2-x1);
		}
		public boolean checkEdge(int x, int y) {
			int  i,a,b;
			if(this.equal(x,y)) return true;
			for(i=1;i<=20;i++) {
				a=x;b=y+i;
				if(this.equal(a,b)) return true;
				a=x;b=y-i;
				if(this.equal(a,b)) return true;
				a=x-i;b=y;
				if(this.equal(a,b)) return true;
				a=x+i;b=y;
				if(this.equal(a,b)) return true;
				a=x+i;b=y+i;
				if(this.equal(a,b)) return true;
				a=x-i;b=y-i;
				if(this.equal(a,b)) return true;
				a=x+i;b=y-i;
				if(this.equal(a,b)) return true;
				a=x-i;b=y+i;
				if(this.equal(a,b)) return true;
			}
			if(i>=10) return false;
			else return true;	
		}
		public boolean equal(int x,int y) {
			int c=((y)-y1)*(x2-x1);
			int d=(y2-y1)*((x)-x1);
			if(c>d-20&&c<d+20) return true;
			else return false;
		}
		}


	//EdgeList Class
	public class EdgeList<Edge> {
		private ArrayList<Edge> edgeList;

		public EdgeList() {
			edgeList=new ArrayList<>();
		}
		public void add(Edge e) {
			edgeList.add(e);
        }
		public Edge get(int i) {
			return edgeList.get(i);
		}
		public ArrayList<Edge> getList(){
			return edgeList;
		}
		public int getIndex(Edge e) {
			return edgeList.indexOf(e);
		}
		public int size() {
			return edgeList.size();
		}
	}

	//Help Class
	protected class Help {
		JFrame frame;
		JTextArea jLabel ;
		JButton btn;
		JPanel guiPanel;
		public Help() {
			this.guiHelp();
		}
		public void guiHelp() {
			frame=new JFrame("HELP");
			frame.setVisible(true);
			frame.setSize(600,600);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			guiPanel=new JPanel();
			guiPanel.setSize(frame.getSize());
			guiPanel.setBackground(Color.WHITE);
			jLabel=new JTextArea();
			jLabel.setBounds(20,20,56,360);
			jLabel.setEditable(false);
			btn=new JButton("OK");
			btn.setBounds(300, 510, 60, 30);
			guiPanel.add(jLabel);
			frame.add(btn);
			jLabel.setText("\n\n"
					+ "    -> Add Vertex\n"
                    +"          :Set Add Vertex and add Vertices on White Space by Left Click of Your Mouse\n\n"
					+ "    -> Add Edge\n"
                    +"          :Select Two Vertices and add Edge between them\n\n"
					+ "    -> Move Vertex to move vertex to another position.\n"
                    +"          :Select a Vertex and move it by dragging to your desired location on White Space\n\n"
					+ "    -> Shortest Path \n"
                    +"          :Select two vertices and route a shortest path between them\n\n"
					+ "    -> Change weight\n"
                    +"          :Enter a Desired weight and Select the desired edge you want to change weight for\n\n"
					+ "    -> Add All Edge\n"
                    +"          :Connects all the vertices individually\n\n"
					+ "    -> Add Random Weight:\n"
                    +"          :Will provide Random Weight to the edges\n\n"
					+ "    -> Minimal Spanning Tree\n"
                    +"          :Gets a Minimum Spanning Tree\n\n"
				);
			frame.add(guiPanel);
            //OK Button in Help
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
		}
		}
	protected class Panel extends JPanel{
		public Panel() {
			this.setBounds(230, 10, 440, 600);
			this.setBackground(Color.GRAY);
			this.setVisible(true);
		 }
	}
}
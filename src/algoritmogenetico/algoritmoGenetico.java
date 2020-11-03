package algoritmogenetico;

import java.util.ArrayList;
import java.util.Random;

public class algoritmoGenetico {
    
    private Random r = new Random(System.currentTimeMillis());
    
    private int[][] Matriz = {
            {0,5,5,7,8,10,12,10},
            {5,0,5,8,6,11,9,12},
            {5,5,0,6,6,6,8,8},
            {8,8,7,0,9,9,11,10},
            {8,6,9,6,0,3,3,6},
            {10,11,9,7,3,0,4,3},
            {11,9,11,13,8,3,0,2},
            {5,0,5,8,6,11,9,12}};
    
    private int TamPopu = 10;
    private int TamBits = Matriz.length;
    
    public algoritmoGenetico(){
        ArrayList<int[]> PopulaçãoInicial = PopulaçãoInicial(TamPopu, TamBits);
        
        //INICIO: IMPRIMIR POPULAÇÂO INICIAL
        for(int i=0;i<PopulaçãoInicial.size();i++){
            int[] Individuo = PopulaçãoInicial.get(i);
            System.out.print("Ind: ");
            for(int x=0;x<Individuo.length;x++) {
    		System.out.print(Individuo[x]+" ");
            }
            System.out.print("\n");
        }
        //FIM: IMPRIMIR POPULAÇÂO INICIAL
        
        //INICIO: FITNESS
        float[] apt = FAptidão(PopulaçãoInicial, TamPopu);
        float soma=0f;
        
        for(int i=0;i<apt.length;i++){
            //apt[i] = Arredondar(apt[i], 2);
            System.out.print("Apt: "+apt[i]+"\n");
            soma += apt[i];
        }
        System.out.print("Soma: "+soma+"\n");
        //FIM: FITNESS
        
    }
    
    public float[] FAptidão(ArrayList<int[]> População,int tp){
        float[] f = new float[tp];
        float soma = 0f;
        
        for(int i=0;i<f.length;i++){
            f[i] = avalia(População.get(i))/1f;
            soma += f[i];
        }
        
        for(int i=0;i<f.length;i++){
            f[i] = f[i] / soma;
        }
        
        return f;
    }
    
    public ArrayList PopulaçãoInicial(int Popu, int bits){
        ArrayList<int[]> População = new ArrayList();
        
        for(int x=0;x<Popu;x++){
            
            int[] Individuo = soluçãoInicial(bits);
        
            População.add(Individuo);
        }
        
        return População;
    }
    
    public int[] soluçãoInicial(int n){
        int[] Sequencia = new int[n];
        
        for(int i=0;i<Sequencia.length;i++){
            Sequencia[i]=i+1;
        }
        
        for(int i=0;i<r.nextInt(14)+6;i++){
            int Ran1 = r.nextInt(Sequencia.length);
            int Ran2 = r.nextInt(Sequencia.length);
            
            int valor = Sequencia[Ran1];
            Sequencia[Ran1] = Sequencia[Ran2];
            Sequencia[Ran2] = valor;
        }
               
        return Sequencia;
    }
    
    public int avalia(int[] Sequencia){
        int soma=0;
        for(int i=0;i<Sequencia.length-1;i++){
            int y = Sequencia[i]-1;
            int x = Sequencia[i+1]-1;
            soma += Matriz[y][x];
        }
        int y = Sequencia[Sequencia.length-1]-1;
        int x = Sequencia[0]-1;
        soma += Matriz[y][x];
        
        return soma;
    }
    
}

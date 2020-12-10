package algoritmogenetico;

import java.util.ArrayList;

public class Utils {
        
    public String printArray(int[] array){
        String pArray="{";
        for(int x=0;x<array.length;x++){
           pArray+=array[x];
           if(x!=array.length-1){pArray+=",";}
        }
        pArray+="}";
        return pArray;
    }
    
    public void printPopulação(String Tipo,ArrayList<int[]> População){
        for(int i=0;i<População.size();i++){
            int[] Individuo = População.get(i);
            System.out.print(Tipo);
            for(int x=0;x<Individuo.length;x++) {
    		System.out.print(Individuo[x]+" ");
            }
            System.out.print("\n");
        }
    }
        
    public ArrayList<int[]> ordena(ArrayList<int[]> PopulaçãoOri, float[] aptOri){
        
        for(int x=0;x<aptOri.length;x++){
            for(int y=0;y<aptOri.length;y++){
                if(x != y){
                    if(aptOri[x] < aptOri[y]){
                        float Ftemp = aptOri[x];
                        aptOri[x] = aptOri[y];
                        aptOri[y] = Ftemp;
                        
                        int[] Ptemp = PopulaçãoOri.get(x);
                        PopulaçãoOri.set(x, PopulaçãoOri.get(y));
                        PopulaçãoOri.set(y, Ptemp);
                    }
                }
            }
        }
        
        return PopulaçãoOri;
    }
    
}

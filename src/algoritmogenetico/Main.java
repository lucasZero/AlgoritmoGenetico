package algoritmogenetico;

public class Main {
    
    private static int[] TamPopu = {10,50,100,200}; //ORIGINAL 50
    private static float[] TaxaCruz = {0.5f,0.6f,0.8f}; //ORIGINAL 0.9
    private static float[] TaxaMuta = {0f,0.05f,0.1f,0.2f}; //ORIGINAL 0.05
    private static float[] InterGen = {0f,0.1f,0.2f,0.5f}; //ORIGINAL ??
    private static int[] NumGen = {10,50,100}; //ORIGINAL 50
    
    public static void main(String[] args) { 
        for(int p1=0;p1<TamPopu.length;p1++){
        for(int p2=0;p2<TaxaCruz.length;p2++){
        for(int p3=0;p3<TaxaMuta.length;p3++){
        for(int p4=0;p4<InterGen.length;p4++){
        for(int p5=0;p5<NumGen.length;p5++){
            new algoritmoGenetico(TamPopu[p1], TaxaCruz[p2], TaxaMuta[p3], InterGen[p4], NumGen[p5]);
        }}}}}
        
    }
}
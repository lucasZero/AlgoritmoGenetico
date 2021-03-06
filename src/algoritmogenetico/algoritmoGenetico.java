package algoritmogenetico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class algoritmoGenetico extends Utils{
    
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
    
    private int TamBits = Matriz.length;
            
    public algoritmoGenetico(int TP,float TC,float TM,float IG,float NG){
        // POPULAÇÃO
        ArrayList<int[]> PopulaçãoInicial = PopulaçãoInicial(TP, TamBits);
        
        ArrayList<int[]> PopulaçãoAtual = null;
        
        for(int GenAt=0;GenAt<NG;GenAt++){
            
            if(PopulaçãoAtual == null){
                PopulaçãoAtual = (ArrayList<int[]>)PopulaçãoInicial.clone();
            }
            
            //FITNESS
            float[]apt = FAptidão(PopulaçãoAtual);
            float soma=0f;
                
            //OPERAÇÃO DE CRUZAMENTO
            ArrayList<int[]> Ger = OpCruzamento(PopulaçãoAtual, apt, TP, TC);
        
            //OPERAÇÃO DE MUTAÇÃO
            ArrayList<int[]> Mut = OpMutação(Ger, apt, TP, TM);
        
            //JUNTA AS POPULAÇÕES GERAÇÃO E MUTANTE
            ArrayList<int[]> NovaPopulação = new ArrayList<>();
            NovaPopulação.addAll(Ger);
            NovaPopulação.addAll(Mut);
        
            //ORGANIZA A POPULAÇÃO ATUAL
            ordena(PopulaçãoAtual,apt);
            
            //GERA A APTIDÃO E ORGANIZA A NOVA POPULAÇÃO   
            float[] Napt = FAptidão(NovaPopulação);
            ordena(NovaPopulação, Napt);
        
            //CRIA A NOVA POPULAÇÃO
            int elite = Math.round(IG*TP);
            
            ArrayList<int[]> NovaGeração = new ArrayList();
                
            for(int i=0;i<elite;i++){
                NovaGeração.add(PopulaçãoAtual.get(i));
            }
            
            for(int i=elite;i<TP;i++){
                NovaGeração.add(NovaPopulação.get(i-elite));
            }
            
            PopulaçãoAtual.clear();
            
            PopulaçãoAtual = (ArrayList<int[]>)NovaGeração.clone();
        }
        ordena(PopulaçãoInicial, FAptidão(PopulaçãoInicial));
        
        int AFinal = avalia(PopulaçãoAtual.get(0));
        
        int AInicial = avalia(PopulaçãoInicial.get(0));
                
        if(AFinal != 0 && VerificaFilho(PopulaçãoAtual.get(0))){
            
            System.out.print(AInicial+"	");
            System.out.print(TP+"	"+TC+"	"+TM+"	"+IG+"	"+NG);
            System.out.print("	"+AFinal+"\n");
        }
        else{
            new algoritmoGenetico(TP, TC, TM, IG, NG);
        }
        
        
    }
    
    public int torneio(float[] fit){
        int lutadorA = r.nextInt(fit.length);
        int lutadorB = r.nextInt(fit.length);
        
        int vencedor = lutadorA;
        
        if(fit[lutadorA] < fit[lutadorB]){
            vencedor = lutadorB;
        }
        
        return vencedor;
    }
    
    public int roleta(float[] fit){
        float valor = r.nextFloat();
        float soma=0;
        
        int vencedor = fit.length-1;
        
        for(int i=0;i<fit.length;i++){
            
            soma = soma + fit[i];
            
            if(soma > valor){
                vencedor = i;
                break;
            }
        }
        
        return vencedor;
    }
    
    public ArrayList<int[]> OpCruzamento(ArrayList<int[]> População,float[] fitness,int tp,float tc){
        ArrayList<int[]> novaGen = new ArrayList<int []>();
        
        int qntCruz = Math.round(tp*tc);
        
        int corte = r.nextInt(TamBits-1)+1;
        
        for(int i=0; i<qntCruz; i++){
            int S_Pai = -1;
            int S_Mae = -1;
            
            while(S_Pai == S_Mae){
                S_Pai = torneio(fitness);
                S_Mae = roleta(fitness);
            }
            
            int[] pai = População.get(S_Pai);
            int[] mae = População.get(S_Mae);
            
            int[] part_pai = Arrays.copyOfRange(pai, corte, pai.length);
            int[] part_mae = Arrays.copyOfRange(mae, corte, pai.length);

            int[] filho1 = Arrays.copyOf(pai, pai.length);
            int[] filho2 = Arrays.copyOf(mae, mae.length);

            System.arraycopy(part_mae, 0, filho1, corte, part_mae.length);
            System.arraycopy(part_pai, 0, filho2, corte, part_pai.length);
            
            filho1 = corrigeFilho(filho1);
            filho2 = corrigeFilho(filho2);
            
            novaGen.add(filho1);
            novaGen.add(filho2);
        }
        
        return novaGen;
    }
    
    public ArrayList<int[]> OpMutação(ArrayList<int[]> População,float[] fitness,int tp,float tm){
        ArrayList<int[]> novaMut = new ArrayList<int []>();
        
        int qntCruz = Math.round(tp*tm);
        
        for(int x=0;x<qntCruz;x++){
            
            int[] Mutante = População.get(r.nextInt(População.size()));
            
            int Crom1 = -1;
            int Crom2 = -1;
            
            while(Crom1 == Crom2){
                Crom1 = r.nextInt(Mutante.length);
                Crom2 = r.nextInt(Mutante.length);
            }
            
            int valor = Mutante[Crom1];
            
            Mutante[Crom1] = Mutante[Crom2];
            
            Mutante[Crom2] = valor;
            
            novaMut.add(Mutante);
        }
        
        return novaMut;
    }
    
    public boolean VerificaFilho(int[] filho){
        boolean OK = true;
        
        int[] check = new int[filho.length];
        
        for(int i=0;i<filho.length;i++){
            check[filho[i]-1]++;
        }
        
        for(int i=0;i<check.length;i++){
            if(check[i] == 0 || check[i] == 2){
                OK=false;
                break;
            }
        }
        
        return OK;
    }
    
    public int[] corrigeFilho(int[] filho){
        
        int[] check = new int[filho.length];
        ArrayList<Integer> NAparece = new ArrayList<>();
        ArrayList<Integer> Repete = new ArrayList<>();
        
        for(int i=0;i<filho.length;i++){
            check[filho[i]-1]++;
        }
        
        for(int i=0;i<check.length;i++){
            if(check[i] == 0){ NAparece.add(i+1); }
            if(check[i] == 2){ Repete.add(i+1); }
        }
        
        for(int i=filho.length-1;i>=0;i--){
            if(NAparece.isEmpty() || Repete.isEmpty()){ break; }
            
            if(filho[i] == Repete.get(0)){
                int ale = r.nextInt(NAparece.size());
                filho[i] = NAparece.get(0);
                Repete.remove(0);
                NAparece.remove(0);
            }
        }
        
        return filho;
    }
    
    public float[] FAptidão(ArrayList<int[]> População){
        float[] f = new float[População.size()];
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

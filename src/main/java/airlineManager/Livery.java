package airlineManager;

public class Livery {


    
    // private File file;
    private String liveryFileName;

    

    public Livery(String liveryFileName) {
        this.liveryFileName = liveryFileName;
    }



    public String getLiveryFileName() {
        return this.liveryFileName;
    }

    @Override
    public String toString() {
        return this.liveryFileName;
    }
}
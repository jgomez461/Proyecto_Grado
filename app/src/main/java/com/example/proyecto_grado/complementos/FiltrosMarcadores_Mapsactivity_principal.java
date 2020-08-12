package com.example.proyecto_grado.complementos;

public class FiltrosMarcadores_Mapsactivity_principal {

    //este es el complemento de AdaptadorCategorias_principal

    // se saca con logt
    private static final String TAG = "FiltrosMarcadores_Mapsa";

    private Integer mnames;
    private Integer categoria;

    public FiltrosMarcadores_Mapsactivity_principal(Integer mnames, Integer categoria) {
        this.mnames = mnames;
        this.categoria = categoria;
    }

    public void setMnames(Integer mnames) {
        this.mnames = mnames;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Integer getMnames() {
        return mnames;
    }

    public Integer getCategoria() {
        return categoria;
    }
}

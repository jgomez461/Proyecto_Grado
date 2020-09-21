package com.example.proyecto_grado.complementos;

public class FiltrosMarcadores_Mapsactivity_principal {

    //este es el complemento de AdaptadorCategorias_principal

    // se saca con logt
    private static final String TAG = "FiltrosMarcadores_Mapsa";

    private Integer mnames;
    private Integer categoria;
    private Integer categoria_secundario;

    public FiltrosMarcadores_Mapsactivity_principal(Integer mnames, Integer categoria, Integer categoria_secundario) {
        this.mnames = mnames;
        this.categoria = categoria;
        this.categoria_secundario = categoria_secundario;
    }

    public Integer getMnames() {
        return mnames;
    }

    public void setMnames(Integer mnames) {
        this.mnames = mnames;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Integer getCategoria_secundario() {
        return categoria_secundario;
    }

    public void setCategoria_secundario(Integer categoria_secundario) {
        this.categoria_secundario = categoria_secundario;
    }
}

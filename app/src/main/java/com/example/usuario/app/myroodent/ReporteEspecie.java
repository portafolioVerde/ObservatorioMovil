package com.example.usuario.app.myroodent;

/**
 * La Clase ReporteEspecie.java es el objeto que hace la función
 * de usuario donde se cnostruyen los campos
 */
public class ReporteEspecie {


    String email, direccion, especie, nombre, doc;
    Long id;
    int img;
    public double latitude,longitude;
    String fecha, hora;

    public ReporteEspecie(){

    }

    /**
     * Instancia de ReporteEspecie y se definen los
     * parametros que se van a utilizar en esta clase
     */
    public ReporteEspecie(double latitude,double longitude,int img,String doc,Long id, String email, String direccion, String especie, String nombre, String fecha,String hora) {
        this.doc = doc;
        this.id = id;
        this.email = email;
        this.direccion = direccion;
        this.especie = especie;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.img = img;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    /**
     * Constructores de cada uno de los parametros que
     * se definieron anteriormente
     */
    public String getHora(){ return hora; }

    public  void setHora(){this.hora = hora;}

    public String getDoc(){
        return doc;
    }

    public void setDoc(String doc){
        this.doc = doc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {

        return fecha;
    }

    public void setFecha(String fecha) {

        this.fecha = fecha;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getImg(){
        return  getImg();
    }

    public void setImg(int img){
        this.img = img;
    }

    @Override
    public String toString(){
        return direccion;
    }
}


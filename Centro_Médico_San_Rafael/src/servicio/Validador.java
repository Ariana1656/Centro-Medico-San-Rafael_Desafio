package servicio;

// NUEVA clase (Fase 2): validaciones reutilizables (y faciles de probar con JUnit)
public class Validador {

    // true si el texto no esta vacio y todos sus caracteres son digitos
    public static boolean soloDigitos(String texto) {
        if (texto.isEmpty()) {
            return false;
        }
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    // Formato dd/MM/yyyy, por ejemplo 15/12/2026
    public static boolean fechaValida(String fecha) {
        if (fecha.length() != 10 || fecha.charAt(2) != '/' || fecha.charAt(5) != '/') {
            return false;
        }
        String textoDia = fecha.substring(0, 2);
        String textoMes = fecha.substring(3, 5);
        String textoAnio = fecha.substring(6, 10);
        if (!soloDigitos(textoDia) || !soloDigitos(textoMes) || !soloDigitos(textoAnio)) {
            return false;
        }
        int dia = Integer.parseInt(textoDia);
        int mes = Integer.parseInt(textoMes);
        int anio = Integer.parseInt(textoAnio);

        // febrero se deja con 29 dias para simplificar
        int[] diasPorMes = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return anio >= 2000 && mes >= 1 && mes <= 12
                && dia >= 1 && dia <= diasPorMes[mes - 1];
    }

    // Formato HH:mm, por ejemplo 09:30
    public static boolean horaValida(String hora) {
        if (hora.length() != 5 || hora.charAt(2) != ':') {
            return false;
        }
        String textoHoras = hora.substring(0, 2);
        String textoMinutos = hora.substring(3, 5);
        if (!soloDigitos(textoHoras) || !soloDigitos(textoMinutos)) {
            return false;
        }
        int horas = Integer.parseInt(textoHoras);
        int minutos = Integer.parseInt(textoMinutos);
        return horas <= 23 && minutos <= 59;
    }
}
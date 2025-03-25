package Recursos.PersonalClassB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateCells {
    public static class Atom {
        public final long x;
        public final long y;
        public final long z;

        public Atom(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public static double findMin(List<Double> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacía");
        }
        double min = values.get(0);
        for (double value : values) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public static double findMax(List<Double> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacía");
        }
        double max = values.get(0);
        for (double value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


    public static List<Double> addValueToList(List<Double> values, double valueToAdd) {
        List<Double> updatedList = new ArrayList<>();
        for (double value : values) {
            updatedList.add(value + valueToAdd);
        }
        return updatedList;
    }

    public static Object[] readPDB(String filePath) throws IOException {
        List<Double> atomsX = new ArrayList<>();
        List<Double> atomsY = new ArrayList<>();
        List<Double> atomsZ = new ArrayList<>();
        List<Double> box = new ArrayList<>();
        List<List> cordenadasYbox  = new ArrayList<>();
        List<String> lines = new ArrayList<>(); // Lista para guardar las líneas leídas
        int numAtoms = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("ATOM") || line.startsWith("HETATM")) {
                    lines.add(line); // Agregar línea a la lista
                    // Usar una expresión regular para dividir, considerando dos o más espacios como delimitador
                    // y también separando cuando hay un número seguido de una letra o viceversa.
                    String[] parts = line.split(" {2,}|(?<=\\d) (?=\\D)|(?<=\\D) (?=\\d)");
                    if (parts.length > 8) { // Asegúrate de que haya suficientes partes
                        double x = Double.parseDouble(parts[5]);
                        double y = Double.parseDouble(parts[6]);
                        double z = Double.parseDouble(parts[7]);
                        atomsX.add(x);
                        atomsY.add(y);
                        atomsZ.add(z);
                        numAtoms++;
                    }
                } else if (line.startsWith("CRYST1")) {
                    String[] parts = line.split(" {2,}|(?<=\\d) (?=\\D)|(?<=\\D) (?=\\d)");
                    if (parts.length > 4) { // Asegúrate de que haya suficientes partes
                        box.add(Double.parseDouble(parts[1]));
                        box.add(Double.parseDouble(parts[2]));
                        box.add(Double.parseDouble(parts[3]));
                    }
                }
            }

            if (box.isEmpty()){
                box.add(0.0);
                box.add(0.0);
                box.add(0.0);
            }

         //condicion para obtner coordenadas negativas y normalizar
            double minX = findMin(atomsX);
            double minY = findMin(atomsY);
            double minZ = findMin(atomsZ);

             if(minX < 0){
                minX = (minX * (-1)) + 0.6;
                atomsX = addValueToList(atomsX, minX);
                double maxX = findMax(atomsX);
                box.set(0, maxX + 0.6);
            }else if(minX > 0){
                minX = (minX * (-1)) + 0.6;
                atomsX = addValueToList(atomsX, minX);
                double maxX = findMax(atomsX);
                box.set(0, maxX + 0.6);
            }else{
                 atomsX = addValueToList(atomsX, 0.6);
                 box.set(0,findMax(atomsX) + 0.6);
             }

            if(minY < 0){
               minY = (minY * (-1)) + 0.6;
               atomsY = addValueToList(atomsY, minY);
                double maxY = findMax(atomsY);
                box.set(1, maxY + 0.6);
            }else if(minY > 0){
                minY = (minY * (-1)) + 0.6;
                atomsY = addValueToList(atomsY, minY);
                double maxY = findMax(atomsY);
                box.set(1, maxY + 0.6);
            }else{
                atomsY = addValueToList(atomsY, 0.6);
                box.set(1,findMax(atomsY) + 0.6);
            }

            if(minZ < 0){
                minZ = (minZ * (-1)) + 0.6;
                atomsZ = addValueToList(atomsZ, minZ);
                double maxZ = findMax(atomsZ);
                box.set(2, maxZ + 0.6);
            }else if(minZ > 0){
                minZ = (minZ * (-1)) + 0.6;
                atomsZ = addValueToList(atomsZ, minZ);
                double maxZ = findMax(atomsZ);
                box.set(2, maxZ + 0.6);
            }else{
                atomsZ = addValueToList(atomsZ, 0.6);
                box.set(2,findMax(atomsZ) + 0.6);
            }

            cordenadasYbox.add(atomsX);
            cordenadasYbox.add(atomsY);
            cordenadasYbox.add(atomsZ);
            cordenadasYbox.add(box);
            cordenadasYbox.add(lines);
        }

        // Aquí podrías guardar las dimensiones de la caja si es necesario
        return new Object[]{cordenadasYbox, numAtoms};
    }
}

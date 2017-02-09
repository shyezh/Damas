package Damas;


public enum TiposPieza {
    NEGRO(1), BLANCO(-1);

    final int moveDir;

    TiposPieza(int moveDir) {
        this.moveDir = moveDir;
    }
}

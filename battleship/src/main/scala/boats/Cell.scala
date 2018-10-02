package boats


class BoatCase(x: Int, y: Int, isTouched: Boolean = false){
    def getX(): Int = {
        return this.x
    }
    def getY(): Int = {
        return this.y
    }
}

object BoatCase{
    implicit def touchedCase(boatCase: BoatCase): BoatCase = {
        new BoatCase(boatCase.getX(), boatCase.getY(), true)
    }
}
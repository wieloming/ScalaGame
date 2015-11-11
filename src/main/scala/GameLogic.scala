sealed trait Tile
case class Empty() extends Tile
case class Wall() extends Tile
case class Grass() extends Tile
case class Troll() extends Tile

case class Vec2D(x: Double, y: Double){
  def +(v2: Vec2D) = Vec2D(x + v2.x, y + v2.y)
}

case class Entity(pos: Vec2D, speed: Vec2D)

class GameLogic() {
  type GameMap = Array[Array[Tile]]

  case class GameState(map: GameMap, entities: List[Entity])

  val mapData = Array.fill[Tile](25, 25)(Grass())
  mapData(4)(5) = Wall()

  var state = GameState(
    mapData,
    List(
      Entity(Vec2D(8, 12), Vec2D(0.1, 0.05)),
      Entity(Vec2D(8, 12), Vec2D(-0.1, 0.05)),
      Entity(Vec2D(8, 12), Vec2D(0.1, 0.5)),
      Entity(Vec2D(8, 12), Vec2D(0.1, -0.05)),
      Entity(Vec2D(8, 12), Vec2D(1, 0.05))
    )
  )

  def getFullMapData(state: GameState): GameMap = {
    val newMapData = Array.fill[Tile](25, 25)(Empty())
    state.entities.foreach {entity =>
      newMapData(entity.pos.x.toInt)(entity.pos.y.toInt) = Troll()
    }
    zipMaps(mapData, newMapData, {
      case (oldT, Empty()) => oldT
      case (_, newT) => newT
    })
  }

  def nextState() = {
    val newState = GameState(
      state.map,
      state.entities.map{e =>
        val newPos = if((e.pos + e.speed) ) e.pos else e.pos + e.speed
        Entity(e.pos + e.speed, e.speed)}
    )
    state = newState

    getFullMapData(newState)
  }

  def zipMaps(map1: GameMap, map2: GameMap, zipper: ((Tile, Tile)) => Tile) =
    map1 zip map2 map {
      case (row1, row2) =>
        row1 zip row2 map zipper
    }
}

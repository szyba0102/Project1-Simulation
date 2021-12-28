package agh.ics.oop;

// enum type that specifies animals direction
public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    EAST_SOUTH,
    SOUTH,
    SOUTH_WEST,
    WEST,
    WEST_NORTH;

    // this method represents animal turning 45°
    public MapDirection one(){
        return switch (this) {
            case EAST -> EAST_SOUTH;
            case WEST -> WEST_NORTH;
            case SOUTH -> SOUTH_WEST;
            case NORTH -> NORTH_EAST;
            case NORTH_EAST -> EAST;
            case EAST_SOUTH -> SOUTH;
            case SOUTH_WEST -> WEST;
            case WEST_NORTH -> NORTH;

        };
    }

    // this method represents animal turning 90°
    public MapDirection two() {
        return switch (this) {
            case EAST -> SOUTH;
            case WEST -> NORTH;
            case SOUTH -> WEST;
            case NORTH -> EAST;
            case NORTH_EAST -> EAST_SOUTH;
            case EAST_SOUTH -> SOUTH_WEST;
            case SOUTH_WEST -> WEST_NORTH;
            case WEST_NORTH -> NORTH_EAST;
        };
    }

    // this method represents animal turning 135°
    public MapDirection three(){
        return switch (this) {
            case NORTH -> EAST_SOUTH;
            case NORTH_EAST -> SOUTH;
            case EAST -> SOUTH_WEST;
            case EAST_SOUTH -> WEST;
            case SOUTH -> WEST_NORTH;
            case SOUTH_WEST -> NORTH;
            case WEST -> NORTH_EAST;
            case WEST_NORTH -> EAST;

        };
    }

    // this method represents animal turning 225°
    public MapDirection five(){
        return switch (this) {
            case NORTH -> SOUTH_WEST;
            case NORTH_EAST -> WEST;
            case EAST -> WEST_NORTH;
            case EAST_SOUTH -> NORTH;
            case SOUTH -> NORTH_EAST;
            case SOUTH_WEST -> EAST;
            case WEST -> EAST_SOUTH;
            case WEST_NORTH -> SOUTH;

        };
    }

    // this method represents animal turning 270°
    public MapDirection six() {
        return switch (this) {
            case EAST -> NORTH;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case NORTH -> WEST;
            case NORTH_EAST -> WEST_NORTH;
            case EAST_SOUTH -> NORTH_EAST;
            case SOUTH_WEST -> EAST_SOUTH;
            case WEST_NORTH -> SOUTH_WEST;

        };
    }

    // this method represents animal turning 315°
    public MapDirection seven(){
        return switch (this) {
            case NORTH -> WEST_NORTH;
            case NORTH_EAST -> NORTH;
            case EAST -> NORTH_EAST;
            case EAST_SOUTH -> EAST;
            case SOUTH -> EAST_SOUTH;
            case SOUTH_WEST -> SOUTH;
            case WEST -> SOUTH_WEST;
            case WEST_NORTH -> WEST;

        };
    }

    // this method change enum type to object Vectord2d
    public Vector2d toUnitVector(){
        return switch (this){
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0,-1);
            case NORTH_EAST -> new Vector2d(1,1);
            case EAST_SOUTH -> new Vector2d(1,-1);
            case SOUTH_WEST -> new Vector2d(-1,-1);
            case WEST_NORTH -> new Vector2d(-1,1);
        };
    }
}

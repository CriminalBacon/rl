package rl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorldBuilder {
    private int width;
    private int height;
    private int depth;
    private Tile[][][] tiles;
    private int[][][] regions;
    private int nextRegion;

    public WorldBuilder(int width, int height, int depth){
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.tiles = new Tile[width][height][depth];
        this.regions = new int[width][height][depth];
        this.nextRegion = 1;

    } // rl.WorldBuilder

    public World build(){
        return new World(tiles);
    } //build

    private WorldBuilder randomizeTiles(){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                for (int z = 0; z < depth; z++) {
                    tiles[x][y][z] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
                } //for z
            } //for y
        } //for z
        return this;
    } //randomizeTiles

    private WorldBuilder smooth(int times){
        Tile[][][] tiles2 = new Tile[width][height][depth];
        for (int time = 0; time < times; time++){
            for (int x = 0; x < width; x++){
                for (int y = 0; y < height; y++){
                    for (int z = 0; z < depth; z++) {
                        int floors = 0;
                        int rocks = 0;

                        for (int ox = -1; ox < 2; ox++) {
                            for (int oy = -1; oy < 2; oy++) {
                                if (x + ox < 0 || x + ox >= width
                                        || y + oy < 0 || y + oy >= height) {
                                    continue;
                                } //if

                                if (tiles[x + ox][y + oy][z] == Tile.FLOOR) {
                                    floors++;
                                } else {
                                    rocks++;
                                } //else

                            } //for oy

                        } // for ox
                        tiles2[x][y][z] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
                    } //for z

                } //for y

            } //for x
            tiles = tiles2;

        } //for times

        return this;

    } //rl.WorldBuilder


    //each location has a number that identifies what region of contiguous open space it belongs to.
    //if two locations have the same region number, then they are connected
    //if it is not a wall and does not have a region assigned, then the empty space and all empty
    //spaces connected to it will be given a new region number.  if the region is too small, it will be
    //removed.

    private WorldBuilder createRegions(){
        regions = new int[width][height][depth];

        for (int z = 0; z < depth; z++){
            for (int x = 0; x < width; x++){
                for (int y = 0; y < height; y++){
                    if (tiles[x][y][z] != Tile.WALL && regions[x][y][z] == 0){
                        int size = fillRegion(nextRegion++, x, y, z);

                        if (size < 25){
                            removeRegion(nextRegion -1 , z);
                        }
                    }
                } //for y

            } //for x


        } //for z

        return this;
    } //createRegions

    //zeros out the region number and fills the cave so it's a solid wall.
    private void removeRegion(int region, int z){
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if (regions[x][y][z] == region){
                    regions[x][y][z] = 0;
                    tiles[x][y][z] = Tile.WALL;
                }
            } //for y
        } //for x
    } //removeRegion

    //does a flood-fill starting with an open tile.  it and any open tile it is connected to gets
    //assigned the same region number.

    private int fillRegion(int region, int x, int y, int z){
        int size = 1;
        List<Point> open = new ArrayList<Point>();
        open.add(new Point(x, y, z));
        regions[x][y][z] = region;

        while (!open.isEmpty()){
            Point p = open.remove(0);

            for (Point neighbor : p.neighbors8()){
                if (neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width || neighbor.y >= height){
                    continue;
                }

                if (regions[neighbor.x][neighbor.y][neighbor.z] > 0 ||
                        tiles[neighbor.x][neighbor.y][neighbor.z] == Tile.WALL){
                    continue;
                } //if

                size++;
                regions[neighbor.x][neighbor.y][neighbor.z] = region;
                open.add(neighbor);

            } //for
        } //while

        return size;

    } //fillRegion

    //connect all regions with stairs.  just start at the top and connect them one layer at time
    public WorldBuilder connectRegions(){
        for (int z = 0; z < depth -1; z++){
            connectRegionsDown(z);
        }
        return this;
    } //connectRegions

    //look at each region that sits above another region.  If they haven't been connected, connect them
    private void connectRegionsDown(int z){
        List<String> connected = new ArrayList<String>();

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                String region = regions[x][y][z] + "," + regions[x][y][z];
                if (tiles[x][y][z] == Tile.FLOOR
                    && tiles[x][y][z+1] == Tile.FLOOR
                    && !connected.contains(region)){
                    connected.add(region);
                    connectRegionsDown(z, regions[x][y][z], regions[x][y][z+1]);
                } //if

            } // for y


        } //for x
    } //connectRegionsDown

    //get a list of all the locations where one region is directly above another.  Then based on
    //how much area overlaps, connect them with stairs going up and stairs going down

    private void connectRegionsDown(int z, int region1, int region2) {
        List<Point> candidates = findRegionOverlaps(z, region1, region2);

        int stairs = 0;
        do {
            Point p = candidates.remove(0);
            tiles[p.x][p.y][z] = Tile.STAIRS_DOWN;
            tiles[p.x][p.y][z + 1] = Tile.STAIRS_UP;
            stairs++;

//        } while (candidates.size() / stairs > 250);
        } while (candidates.size() / stairs > 850);
    } //connectRegionsDown


    public List<Point> findRegionOverlaps(int z, int region1, int region2){
        ArrayList<Point> candidates = new ArrayList<Point>();

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                if (tiles[x][y][z] == Tile.FLOOR
                        && tiles[x][y][z+1] == Tile.FLOOR
                        && regions[x][y][z] == region1
                        && regions[x][y][z+1] == region2){
                    candidates.add(new Point(x, y, z));
                }
            } //for y

        } //for x

        Collections.shuffle(candidates);
        return candidates;

    } //findRegionOverlaps


    public WorldBuilder makeCaves(){
        return randomizeTiles()
                .smooth(8)
                .createRegions()
                .connectRegions();
    } //makeCaves

} //class rl.WorldBuilder


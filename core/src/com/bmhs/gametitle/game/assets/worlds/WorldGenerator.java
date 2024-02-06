package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;



public class WorldGenerator {

    private int worldMapRows, worldMapColumns;

    private int[][] worldIntMap;

    private int seedColor, lightGreen, teal, lightBlue;

    public WorldGenerator (int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];

        seedColor = 2;
        lightGreen = 17;
        teal = 18;
        lightBlue = 19;

        //call methods to build 2D array

        oceanBackground();
        oceanBackGroundExpansion();

        seedIslands(5);
        searchAndExpand(15, seedColor, lightGreen, 1);
        searchAndExpand(10, seedColor, teal, 0.7);
        searchAndExpand(6, seedColor, lightBlue, 0.5);


        /**

        for (int n = 0; n < 5; n++) {
            Vector2 mapSeed = new Vector2(MathUtils.random(worldIntMap[0].length), MathUtils.random(worldIntMap.length));
            System.out.println(mapSeed.y + " " + mapSeed.x);

            worldIntMap[(int) mapSeed.y][(int) mapSeed.x] = 4;


            for (int r = 0; r < worldIntMap.length; r++) {
                for (int c = 0; c < worldIntMap[r].length; c++) {
                    Vector2 tempVector = new Vector2(c, r);
                    if (tempVector.dst(mapSeed) < 10) {
                        worldIntMap[r][c] = 17;
                    }
                }
            }
        }
         */

// ahhhh

        //call methods to build 2D array

        // randomize();
        // centerLake();
        // leftCoast();

        generateWorldTextFile();

        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
    }

    private void seedIslands(int num) {
        for(int i = 0; i < num; i++){
            int rSeed = MathUtils.random(worldIntMap.length-1);
            int cSeed = MathUtils.random(worldIntMap[0].length-1);
            worldIntMap[rSeed][cSeed] = seedColor;
        }
    }

    //extension for searchAndExpand to get rid of seed while not overwriting other island seeds:
    // (worldIntMap[subRow][subCol] != seedColor || worldIntMap[r][c] == seedColor)

    private void firstSearchAndExpand(int radius) {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {

                if(worldIntMap[r][c] == seedColor) {

                    for(int subRow = r-radius; subRow <= r+radius; subRow++) {
                        for(int subCol = c-radius; subCol <= c+radius; subCol++){

                            if(subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length-1 && subCol <= worldIntMap[0].length-1 && worldIntMap[subRow][subCol] != seedColor) {
                                worldIntMap[subRow][subCol] = 3;
                            }

                        }
                    }

                }


            }
        }
    }

    private void searchAndExpand(int radius) {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {

                if(worldIntMap[r][c] == seedColor) {

                    for(int subRow = r-radius; subRow <= r+radius; subRow++) {
                        for(int subCol = c-radius; subCol <= c+radius; subCol++){

                            if(subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length-1 && subCol <= worldIntMap[0].length-1 && worldIntMap[subRow][subCol] != seedColor) {
                                worldIntMap[subRow][subCol] = 3;
                            }

                        }
                    }

                }


            }
        }
    }

    private void searchAndExpand(int radius, int numToFind, int numToWrite, double probability) {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                if(worldIntMap[r][c] == numToFind) {
                    for(int subRow = r-radius; subRow <= r+radius; subRow++) {
                        for(int subCol = c-radius; subCol <= c+radius; subCol++){
                            if(Math.pow(Math.abs(r-subRow), 2) + Math.pow(Math.abs(c-subCol), 2) <= Math.pow(radius, 2) && subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length-1 && subCol <= worldIntMap[0].length-1 && worldIntMap[subRow][subCol] != numToFind) {
                                if(Math.random() < probability) {
                                    worldIntMap[subRow][subCol] = numToWrite;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String getWorld3DArrayToString() {
        String returnString = "";

        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                returnString += worldIntMap[r][c] + " ";
            }
            returnString += "\n";
        }

        return returnString;
    }

    public void centerLake() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                if( Math.pow((r - 50), 2) + Math.pow((c - 80), 2)  < 2000) {
                    worldIntMap[r][c] = 20;
                }
            }
        }
    }

    public void leftCoast() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                if(c < 10) {
                    worldIntMap[r][c] = 20;
                }
            }
        }
    }

    public void oceanBackground() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                if(Math.random() < 0.005) {
                    worldIntMap[r][c] = 9;
                }
                else{
                    worldIntMap[r][c] = 24;
                }
            }
        }
    }

    public void oceanBackGroundExpansion() {
        int radius = 5;
        double randomNum;
        for(int i = 0; i < 10; i++) {
            for (int r = 0; r < worldIntMap.length; r++) {
                for (int c = 0; c < worldIntMap[r].length; c++) {
                    randomNum = Math.random();
                    if (worldIntMap[r][c] == 9 && r - 1 >= 0 && c - 1 >= 0 && r + 1 <= worldIntMap.length - 1 && c + 1 <= worldIntMap[0].length - 1) {
                        if (randomNum < 0.3) {
                            worldIntMap[r - 1][c - 1] = 22;
                        }
                        if (randomNum > 0.0 && randomNum < 0.2) {
                            worldIntMap[r][c - 1] = 22;
                        }
                        if (randomNum > 0.2 && randomNum < 0.3) {
                            worldIntMap[r + 1][c - 1] = 22;
                        }
                        if (randomNum > 0.3 && randomNum < 0.4) {
                            worldIntMap[r - 1][c + 1] = 22;
                        }
                        if (randomNum > 0.4 && randomNum < 0.5) {
                            worldIntMap[r][c + 1] = 22;
                        }
                        if (randomNum > 0.5 && randomNum < 0.6) {
                            worldIntMap[r + 1][c + 1] = 22;
                        }
                        if (randomNum > 0.6 && randomNum < 0.7) {
                            worldIntMap[r - 1][c] = 22;
                        }
                        if (randomNum > 0.7 && randomNum < 0.8) {
                            worldIntMap[r + 1][c] = 22;
                        }
                    }
                }
            }
            for (int r = 0; r < worldIntMap.length; r++) {
                for (int c = 0; c < worldIntMap[r].length; c++) {
                    if (worldIntMap[r][c] == 22) {
                        worldIntMap[r][c] = 9;
                    }
                }
            }
        }
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if (worldIntMap[r][c] == 9) {
                    worldIntMap[r][c] = 22;
                }
            }
        }
    }

    /**
    Island concept code:
    public void oceanBackGroundExpansion() {
        int radius = 5;
        double randomNum;
        for(int i = 0; i < 10; i++) {
            for (int r = 0; r < worldIntMap.length; r++) {
                for (int c = 0; c < worldIntMap[r].length; c++) {
                    randomNum = Math.random();
                    if (worldIntMap[r][c] == 9 && r - 1 >= 0 && c - 1 >= 0 && r + 1 <= worldIntMap.length - 1 && c + 1 <= worldIntMap[0].length - 1) {
                        if (randomNum < 0.1) {
                            worldIntMap[r - 1][c - 1] = 22;
                        }
                        if (randomNum > 0.1 && randomNum < 0.2) {
                            worldIntMap[r][c - 1] = 22;
                        }
                        if (randomNum > 0.2 && randomNum < 0.3) {
                            worldIntMap[r + 1][c - 1] = 22;
                        }
                        if (randomNum > 0.3 && randomNum < 0.4) {
                            worldIntMap[r - 1][c + 1] = 22;
                        }
                        if (randomNum > 0.4 && randomNum < 0.5) {
                            worldIntMap[r][c + 1] = 22;
                        }
                        if (randomNum > 0.5 && randomNum < 0.6) {
                            worldIntMap[r + 1][c + 1] = 22;
                        }
                        if (randomNum > 0.6 && randomNum < 0.7) {
                            worldIntMap[r - 1][c] = 22;
                        }
                        if (randomNum > 0.7 && randomNum < 0.8) {
                            worldIntMap[r + 1][c] = 22;
                        }
                    }
                }
            }
            for (int r = 0; r < worldIntMap.length; r++) {
                for (int c = 0; c < worldIntMap[r].length; c++) {
                    if (worldIntMap[r][c] == 22) {
                        worldIntMap[r][c] = 9;
                    }
                }
            }
        }
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {
                if (worldIntMap[r][c] == 9) {
                    worldIntMap[r][c] = 22;
                }
            }
        }
    }
     */

    public void randomize() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size-1);
            }
        }
    }

    public WorldTile[][] generateWorld() {
        WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
            }
        }
        return worldTileMap;
    }

    private void generateWorldTextFile() {
        FileHandle file = Gdx.files.local("assets/worlds/world.txt");
        file.writeString(getWorld3DArrayToString(), false);
    }

}

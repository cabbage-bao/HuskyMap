package huskymaps.server.logic;

import huskymaps.params.RasterRequest;
import huskymaps.params.RasterResult;

import java.util.Objects;

import static huskymaps.utils.Constants.ROOT_ULLAT;
import static huskymaps.utils.Constants.ROOT_ULLON;
import static huskymaps.utils.Constants.LAT_PER_TILE;
import static huskymaps.utils.Constants.LON_PER_TILE;
import static huskymaps.utils.Constants.ROOT_LAT_DIFF;
import static huskymaps.utils.Constants.ROOT_LON_DIFF;
import static huskymaps.utils.Constants.NUM_X_TILES_AT_DEPTH;
import static huskymaps.utils.Constants.NUM_Y_TILES_AT_DEPTH;
import static huskymaps.utils.Constants.MIN_ZOOM_LEVEL;
import static huskymaps.utils.Constants.MIN_X_TILE_AT_DEPTH;
import static huskymaps.utils.Constants.MIN_Y_TILE_AT_DEPTH;

/** Application logic for the RasterAPIHandler. */
public class Rasterer {

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param request RasterRequest
     * @return RasterResult
     */
    public static RasterResult rasterizeMap(RasterRequest request) {
        //System.out.println("Since you haven't implemented rasterizeMap, nothing is displayed in your browser.");
        double diffLRx = request.lrlon - ROOT_ULLON;
        double diffLRy = ROOT_ULLAT - request.lrlat;
        double diffULx = request.ullon - ROOT_ULLON;
        double diffULy = ROOT_ULLAT - request.ullat;
        //int ulX = diffULx >= 0? (int) (diffULx / LON_PER_TILE[request.depth]) : 0;

        int ulY;
        if (diffULy >= 0) {
            ulY = (int) (diffULy / LAT_PER_TILE[request.depth]);
        } else {
            ulY = 0;
        }

        int ulX;
        if (diffULx >= 0) {
            ulX = (int) (diffULx / LON_PER_TILE[request.depth]);
        } else {
            ulX = 0;
        }

        int lrY;
        if (diffLRy > ROOT_LAT_DIFF) {
            lrY = NUM_Y_TILES_AT_DEPTH[request.depth];
        } else {
            lrY = (int) (diffLRy / LAT_PER_TILE[request.depth]);
        }

        int lrX;
        if (diffLRx > ROOT_LON_DIFF) {
            lrX = NUM_X_TILES_AT_DEPTH[request.depth];
        } else {
            lrX = (int) (diffLRx / LON_PER_TILE[request.depth]);
        }

        Tile[][] grid;
        if (diffLRy <= 0 || diffLRx <= 0 || diffULy >= ROOT_LAT_DIFF || diffULx >= ROOT_LON_DIFF) {
            grid = new Tile[1][1];
            grid[0][0] = new Tile(request.depth, 1, 0);
        }

        grid = new Tile[lrY - ulY + 1][lrX - ulX + 1];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Tile(request.depth, ulX + j, ulY + i);
            }
        }
        return new RasterResult(grid);
    }

    public static class Tile {
        public final int depth;
        public final int x;
        public final int y;

        public Tile(int depth, int x, int y) {
            this.depth = depth;
            this.x = x;
            this.y = y;
        }

        public Tile offset() {
            return new Tile(depth, x + 1, y + 1);
        }

        /**
         * Return the latitude of the upper-left corner of the given slippy map tile.
         * @return latitude of the upper-left corner
         * @source https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
         */
        public double lat() {
            double n = Math.pow(2.0, MIN_ZOOM_LEVEL + depth);
            int slippyY = MIN_Y_TILE_AT_DEPTH[depth] + y;
            double latRad = Math.atan(Math.sinh(Math.PI * (1 - 2 * slippyY / n)));
            return Math.toDegrees(latRad);
        }

        /**
         * Return the longitude of the upper-left corner of the given slippy map tile.
         * @return longitude of the upper-left corner
         * @source https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
         */
        public double lon() {
            double n = Math.pow(2.0, MIN_ZOOM_LEVEL + depth);
            int slippyX = MIN_X_TILE_AT_DEPTH[depth] + x;
            return slippyX / n * 360.0 - 180.0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Tile tile = (Tile) o;
            return depth == tile.depth &&
                    x == tile.x &&
                    y == tile.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(depth, x, y);
        }

        @Override
        public String toString() {
            return "d" + depth + "_x" + x + "_y" + y + ".jpg";
        }
    }
}

package com.bgsoftware.superiorskyblock.generator;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public final class WorldGenerator extends ChunkGenerator {

    private static final SuperiorSkyblockPlugin plugin = SuperiorSkyblockPlugin.getPlugin();

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 100, 0);
    }

    public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
        byte[][] blockSections = new byte[world.getMaxHeight() / 16][];

        if(world.getEnvironment() == World.Environment.NORMAL) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    biomes.setBiome(x, z, Biome.PLAINS);
                }
            }

            if(chunkX == 0 && chunkZ == 0){
                setBlock(blockSections, 0, 99, 0, 7);
            }
        }

        return blockSections;
    }

    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
        ChunkData chunkData = createChunkData(world);

        if(world.getEnvironment() == World.Environment.NORMAL){
            plugin.getNMSAdapter().setBiome(biomes, Biome.PLAINS);

            if(chunkX == 0 && chunkZ == 0){
                chunkData.setBlock(0, 99, 0, Material.BEDROCK);
            }
        }

        return chunkData;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return new ArrayList<>();
    }

    @SuppressWarnings("SameParameterValue")
    private void setBlock(byte[][] blocks, int x, int y, int z, int blockId){
        if(blocks[y >> 4] == null)
            blocks[y >> 4] = new byte[4096];

        blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) blockId;
    }

}
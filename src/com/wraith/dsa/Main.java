package com.wraith.dsa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main
{
    public static void testMemory(int amountOfTests, String testFile)
    {
        if(amountOfTests == 0)
            return;

        System.out.println("------------------------------------------");
        System.out.printf("Test \"Memory\": %d tests || %s file\n", amountOfTests, testFile);

        Runtime runtime = Runtime.getRuntime();
        long memoryBefore, memoryAfter;

        System.gc();
        memoryBefore = memoryAfter = 0;

        for(int i = 0; i < amountOfTests; i++)
        {
            System.gc();
            memoryBefore = runtime.totalMemory() - runtime.freeMemory();
            RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();

            try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
            {
                String line;

                while ((line = br.readLine()) != null)
                    redBlackTree.insert(Integer.parseInt(line));
            }
            catch (IOException e) { e.printStackTrace(); }
            memoryAfter += (runtime.totalMemory() - runtime.freeMemory()-memoryBefore)/(1024*1024);
        }
        System.out.printf("Red-Black Tree: %d MB\n", memoryAfter/amountOfTests);
        //////////////////
        System.gc();
        memoryBefore = memoryAfter = 0;

        for(int i = 0; i < amountOfTests; i++)
        {
            System.gc();
            memoryBefore = runtime.totalMemory() - runtime.freeMemory();
            AVLTree<Integer> avlTree = new AVLTree<>();

            try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
            {
                String line;

                while ((line = br.readLine()) != null)
                    avlTree.root = avlTree.insert(avlTree.root, Integer.parseInt(line));
            }
            catch (IOException e) { e.printStackTrace(); }
            memoryAfter += (runtime.totalMemory() - runtime.freeMemory()-memoryBefore)/(1024*1024);
        }
        System.out.printf("AVL Tree: %d MB\n", memoryAfter/amountOfTests);
        ///////////////////////
        System.gc();
        memoryBefore = memoryAfter = 0;

        for(int i = 0; i < amountOfTests; i++)
        {
            System.gc();
            memoryBefore = runtime.totalMemory() - runtime.freeMemory();
            HashMapChaining<String, Integer> hashMapChaining = new HashMapChaining<>();

            try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
            {
                String line;

                while ((line = br.readLine()) != null)
                    hashMapChaining.insert(line, Integer.parseInt(line));
            }
            catch (IOException e) { e.printStackTrace(); }
            memoryAfter += (runtime.totalMemory() - runtime.freeMemory()-memoryBefore)/(1024*1024);
        }
        System.out.printf("HashMap(Chaining): %d MB\n", memoryAfter/amountOfTests);
        ///////////////////////
        System.gc();
        memoryBefore = memoryAfter = 0;

        for(int i = 0; i < amountOfTests; i++)
        {
            System.gc();
            memoryBefore = runtime.totalMemory() - runtime.freeMemory();
            HashMapLinear<String, Integer> hashMapLinear = new HashMapLinear<>();

            try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
            {
                String line;

                while ((line = br.readLine()) != null)
                    hashMapLinear.insert(line, Integer.parseInt(line));
            }
            catch (IOException e) { e.printStackTrace(); }
            memoryAfter += (runtime.totalMemory() - runtime.freeMemory()-memoryBefore)/(1024*1024);
        }
        System.out.printf("HashMap(Linear): %d MB\n", memoryAfter/amountOfTests);
    }
    public static void testInsert(int amountOfTests, String testFile)
    {
        if(amountOfTests == 0)
            return;

        System.out.println("------------------------------------------");
        System.out.printf("Test \"Insert\": %d tests || %s file\n", amountOfTests, testFile);

        long end;
        long start;

        //////////////////////////////////////
        System.out.print("Red-Black Tree: ");

        start = System.nanoTime();

        for(int i = 0; i < amountOfTests; i++)
        {
            RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();

            try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
            {
                String line;

                while ((line = br.readLine()) != null)
                    redBlackTree.insert(Integer.parseInt(line));
            }
            catch (IOException e) { e.printStackTrace(); }
        }
        end = System.nanoTime() - start;
        System.out.printf("%fs\n", (TimeUnit.NANOSECONDS.toMillis(end)/amountOfTests)/1000.0F);
        //////////////////////////////////////

        System.out.print("AVL Tree: ");
        start = System.nanoTime();

        for(int i = 0; i < amountOfTests; i++)
        {
            AVLTree<Integer> avlTree = new AVLTree<>();

            try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
            {
                String line;

                while ((line = br.readLine()) != null)
                    avlTree.root = avlTree.insert(avlTree.root, Integer.parseInt(line));
            }
            catch (IOException e) { e.printStackTrace(); }
        }
        end = System.nanoTime() - start;
        System.out.printf("%fs\n", (TimeUnit.NANOSECONDS.toMillis(end)/amountOfTests)/1000.0F);

        //////////////////////////////////////

        System.out.print("HashTable(Chain): ");

        start = System.nanoTime();

        for(int i = 0; i < amountOfTests; i++)
        {
            HashMapChaining<String, Integer> hashMapChaining = new HashMapChaining<>();

            try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
            {
                String line;

                while ((line = br.readLine()) != null)
                    hashMapChaining.insert(line, Integer.parseInt(line));
            }
            catch (IOException e) { e.printStackTrace(); }
        }
        end = System.nanoTime() - start;
        System.out.printf("%fs\n", (TimeUnit.NANOSECONDS.toMillis(end)/amountOfTests)/1000.0F);

        //////////////////////////////////////

        System.out.print("HashTable(Linear): ");

        start = System.nanoTime();

        for(int i = 0; i < amountOfTests; i++)
        {
            HashMapLinear<String, Integer> hashMapLinear = new HashMapLinear<>();

            try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
            {
                String line;

                while ((line = br.readLine()) != null)
                    hashMapLinear.insert(line, Integer.parseInt(line));
            }
            catch (IOException e) { e.printStackTrace(); }
        }
        end = System.nanoTime() - start;
        System.out.printf("%fs\n", (TimeUnit.NANOSECONDS.toMillis(end)/amountOfTests)/1000.0F);
    }
    public static void testSearch(int amountOfTests, String testFile, String hashValue, int intValue)
    {
        if(amountOfTests == 0)
            return;

        System.out.println("------------------------------------------");
        System.out.printf("Test \"Search\": %d tests || %s file\n", amountOfTests, testFile);

        int value = 0;
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        AVLTree<Integer> avlTree = new AVLTree<>();
        HashMapChaining<String, Integer> hashMapChaining = new HashMapChaining<>();
        HashMapLinear<String, Integer> hashMapLinear = new HashMapLinear<>();

        try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                value = Integer.parseInt(line);
                redBlackTree.insert(value);
            }
        }
        catch (IOException e) { e.printStackTrace(); }

        long end;
        long start;

        //////////////////////////////////////
        System.out.print("Red-Black Tree: ");
        start = System.nanoTime();

        for(int i = 0; i < amountOfTests; i++)
            redBlackTree.search(intValue);

        end = System.nanoTime() - start;
        System.out.printf("%fs\n", (float)end/amountOfTests/100000000F);
        redBlackTree = null;
        //////////////////////////////////////

        try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                value = Integer.parseInt(line);
                avlTree.root = avlTree.insert(avlTree.root, value);
            }
        }
        catch (IOException e) { e.printStackTrace(); }

        System.out.print("AVL Tree: ");
        start = System.nanoTime();

        for(int i = 0; i < amountOfTests; i++)
            avlTree.search(intValue);

        end = System.nanoTime() - start;
        System.out.printf("%fs\n", (float)end/amountOfTests/100000000F);
        avlTree = null;
        //////////////////////////////////////
        try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                value = Integer.parseInt(line);
                hashMapChaining.insert(line, value);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        System.out.print("HashTable(Chain): ");
        start = System.nanoTime();

        for(int i = 0; i < amountOfTests; i++)
            hashMapChaining.search(hashValue);

        end = System.nanoTime() - start;
        System.out.printf("%fs\n", (float)end/amountOfTests/100000000F);
        hashMapChaining = null;
        //////////////////////////////////////
        try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                value = Integer.parseInt(line);
                hashMapLinear.insert(line, value);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        System.out.print("HashTable(Linear): ");
        start = System.nanoTime();

        for(int i = 0; i < amountOfTests; i++)
            hashMapLinear.search(hashValue);

        end = System.nanoTime() - start;
        System.out.printf("%fs\n", (float)end/amountOfTests/100000000F);
        hashMapLinear = null;
    }
    public static float[] testDelete(String testFile, String hashValue, int intValue)
    {
        int value;
        float[] time = new float[4];

        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        AVLTree<Integer> avlTree = new AVLTree<>();
        HashMapChaining<String, Integer> hashMapChaining = new HashMapChaining<>();
        HashMapLinear<String, Integer> hashMapLinear = new HashMapLinear<>();

        long end;
        long start;

        //////////////////////////////////////
        try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                value = Integer.parseInt(line);
                redBlackTree.insert(value);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        start = System.nanoTime();
        redBlackTree.delete(intValue);
        end = System.nanoTime() - start;
        time[0] = (float)end/100000000F;
        //////////////////////////////////////
        try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                value = Integer.parseInt(line);
                avlTree.root = avlTree.insert(avlTree.root, value);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        start = System.nanoTime();
        avlTree.delete(avlTree.root, intValue);
        end = System.nanoTime() - start;
        time[1] = (float)end/100000000F;
        //////////////////////////////////////
        try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                value = Integer.parseInt(line);
                hashMapChaining.insert(line, value);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        start = System.nanoTime();
        hashMapChaining.delete(hashValue);
        end = System.nanoTime() - start;
        time[2] = (float)end/100000000F;
        //////////////////////////////////////
        try (BufferedReader br = new BufferedReader(new FileReader(testFile)))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                value = Integer.parseInt(line);
                hashMapLinear.insert(line, value);
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        start = System.nanoTime();
        hashMapLinear.delete(hashValue);
        end = System.nanoTime() - start;
        time[3] = (float)end/100000000F;

        return time;
    }
    public static void delete(int deleteTests, String[] files, String hashValue, int intValue)
    {
        if(deleteTests == 0)
            return;

        float[][] deleteTime = new float[4][4];
        float[][] deleteTimeReturn = new float[4][4];

        for(int i = 0; i < deleteTests; i++)
        {
            for(int f = 0; f < files.length; f++)
                deleteTimeReturn[f] = testDelete(files[f], hashValue, intValue);

            for(int j = 0; j < 4; j++)
                for(int k = 0; k < 4; k++)
                    deleteTime[j][k] += deleteTimeReturn[j][k]/(float)deleteTests;
        }

        for(int i = 0; i < 4; i++)
        {
            System.out.println("------------------------------------------");
            System.out.printf("Test \"Delete\": %d tests || %s file\n", deleteTests, files[i]);
            System.out.printf("Red-Black Tree: %fs\n", deleteTime[i][0]);
            System.out.printf("AVL Tree: %fs\n", deleteTime[i][1]);
            System.out.printf("HashTable(Chain): %fs\n", deleteTime[i][2]);
            System.out.printf("HashTable(Linear): %fs\n", deleteTime[i][3]);
        }
    }

    public static void main(String[] args)
    {
        int memoryTests = 10;
        int insertTests = 10;
        int searchTests = 10;
        int deleteTests = 10;

        String[] files = new String[] {
                "dataset/1mil.txt",
                "dataset/10mil.txt",
                "dataset/15mil.txt",
                "dataset/20mil.txt"
        };

        for(String file: files) testMemory(memoryTests, file);

        for (String file : files) testInsert(insertTests, file);
        for (String file : files) testSearch(searchTests, file, "31050405", 31050405);

        System.out.println("Searching non-existent element:");
        for (String file : files) testSearch(searchTests, file, "3105045", 3105045);

        delete(deleteTests, files, "31050405", 31050405);

        System.out.println("Deleting non-existent element:");
        delete(deleteTests, files, "3105045", 3105045);
    }
}

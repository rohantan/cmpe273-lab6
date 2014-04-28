package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting Cache Client...");
		int bucket;

		List<CacheServiceInterface> serverlist = new ArrayList<CacheServiceInterface>();
		serverlist.add(new DistributedCacheService("http://localhost:3000"));
		serverlist.add(new DistributedCacheService("http://localhost:3001"));
		serverlist.add(new DistributedCacheService("http://localhost:3002"));

		CharSequence val;
		for(int key=1;key<=10;key++)
		{
			val =generateRandomChar();
			bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), serverlist.size());
			serverlist.get(bucket).put(key, val.toString());

			System.out.println("put (key "+key +" and value "+ val+")" +" in bucket "+bucket);
		}
		for(int key=1;key<=10;key++)
		{
			bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), serverlist.size());
			System.out.println("get (key "+key +" and value "+ serverlist.get(bucket).get(key) +")" + "from bucket " + bucket);
		}

		System.out.println("Exiting Cache Client...");
	}

	public static CharSequence generateRandomChar(){
		String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int character=(int)(Math.random()*26);
		CharSequence s=alphabet.substring(character, character+1);
		return s;
	}

}

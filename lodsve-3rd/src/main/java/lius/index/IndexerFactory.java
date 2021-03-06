/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lius.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lius.config.LiusConfig;
import lius.util.LiusUtils;

import org.apache.log4j.Logger;


/**
 * @author Rida Benjelloun (ridabenjelloun@gmail.com) 
 */

public class IndexerFactory {
	static Logger logger = Logger.getRootLogger();

	private static Indexer indexer = null;

	public static Indexer getIndexer(File file, LiusConfig lc) {

		try {
			indexer = getIndexer(new FileInputStream(file),MimeTypeUtils.getMimeType(file) , lc);
			indexer.setDocToIndexPath(file.getAbsolutePath());

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
		return indexer;
	}

	public static Indexer getIndexer(InputStream is, LiusConfig lc) {
		return getIndexer(is, MimeTypeUtils.getMimeType(is), lc);
	}

	public static Indexer getIndexer(URL url, LiusConfig lc) {
		try {
			indexer.setDocToIndexPath(url.toString());
			indexer = getIndexer(url.openStream(), MimeTypeUtils.getMimeType(url), lc);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return indexer;
	}

	public static Indexer getIndexer(InputStream is, String mimeType,
									 LiusConfig lc) {


		Map mimeTypesClasses = lc.getMimeTypeClass();
		if(mimeType == null || !mimeTypesClasses.containsKey(mimeType)){
			return null;
		}
		String className = (String) mimeTypesClasses.get(mimeType);
		Class indexerClass = null;
		if(className != null){
			try {
				logger.debug("Class = "+className + " MimeType = "+mimeType);

				indexerClass = Class.forName(className);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				indexer = (Indexer) indexerClass.newInstance();
			} catch (InstantiationException e) {
				logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage());
			}
			indexer.setUp(lc);
			indexer.setMimeType(mimeType);
			indexer.setStreamToIndex(is);
		}
		return indexer;
	}


	// This methode return a list of Indexer's, from directory or ZIP file
	public static List getIndexers(Object toIndex, LiusConfig lc) {

		List indexersLs = new ArrayList();
		if (toIndex instanceof File) {
			File toIndexF = (File) toIndex;
			File[] lf = null;
			if (toIndexF.isDirectory()) {
				lf = toIndexF.listFiles();
				for (int i = 0; i < lf.length; i++) {
					indexersLs.add(getIndexer(lf[i], lc));
				}
			}
			else if (MimeTypeUtils.getMimeType(toIndexF).equals(
					"application/zip")) {
				List files = null;
				try {
					files = LiusUtils.unzip(new FileInputStream(toIndexF));
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage());
				}
				for (int i = 0; i < files.size(); i++) {
					indexersLs.add(getIndexer((File) files.get(i), lc));
				}
			}
		}

		return indexersLs;
	}

	public static List getIndexersFromZipInputStream(InputStream is, LiusConfig lc) {

		List indexers = new ArrayList();
		List files = LiusUtils.unzip(is);

		for (int i = 0; i < files.size(); i++) {
			indexer = getIndexer((File) files.get(i), lc);

			indexer.setFileName(((File)files.get(i)).getName());
			indexers.add(indexer);
		}

		return indexers;
	}



}
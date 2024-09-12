package com.example.krang.services;

import com.example.krang.entities.Album;
import com.example.krang.entities.Media;
import com.example.krang.repository.AlbumRepository;
import com.example.krang.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

  @Autowired
    MediaRepository mediaRepository;
  @Autowired
    AlbumRepository albumRepository;

  public Album addMediaToAlbum(Long albumId, Long mediaId, int order) {

      Optional<Album> optionalAlbum = albumRepository.findById(albumId);
      Optional<Media> optionalMedia = mediaRepository.findById(mediaId);

      if(optionalMedia.isPresent()&& optionalMedia.isPresent()){
          Album album = optionalAlbum.get();
          Media media = optionalMedia.get();
          List<Media> mediaList = album.getMediaList();
          mediaList.add(order-1,media);
          return albumRepository.save(album);
      }
      return null;

  }
  public Album createAlbum(Album album) {

      return albumRepository.save(album);
  }
  public List<Album> getAllAlbums(){
      return albumRepository.findAll();
  }
  public void deleteAlbum(){

  }

}

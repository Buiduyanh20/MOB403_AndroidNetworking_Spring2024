const express = require('express');
const Router = express.Router();
const {getCommentbyIdComic, postComment, getDetailComicById, readComic} = require('../controllers/commentController');

Router.get('/comic/comments', getCommentbyIdComic);
Router.post('/comic/postComments', postComment);


Router.get('/getDetailComicById/:comicId', getDetailComicById);

Router.get('/readComic/:comicId', readComic);

module.exports = Router;
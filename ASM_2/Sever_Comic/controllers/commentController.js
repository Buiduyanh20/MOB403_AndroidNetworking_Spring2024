const comment = require('../models/comment');
const express = require('express');
const { convertleObject } = require('../utils/convertObj');
const user = require('../models/user');
const comic = require('../models/comic');


const getCommentbyIdComic = async (req, res) => {
  try {
    let { _id } = req.query
    let commentlist = await comment.find({ comicId: _id }, { comicId: 0, _id: 0 }).populate('userId', 'fullname')
    res.send(commentlist)
  } catch (error) {
    res.status(500).json({ message: error.message })
  }
}

const postComment = async (req, res) => {
  try {
    let { comicId, userId, content, commenttime } = req.body
    await comment.create({ comicId, userId, content, commenttime })
    res.send({ comicId, userId, content, commenttime })
  } catch (error) {
    res.status(500).json({ message: error.message })
  }
};


const getDetailComicById = async (req, res)=>{
  const comicId = req.params.comicId;

  const c = await comic.findById(comicId);
  const listComments = await comment.find({comicId: comicId});

  for (let x of listComments) {
    const newComment = x;
    const u = await user.findById(x.userId);
    const c = await comic.findById(x.comicId);
    if (u !== null && c !== null) {
      x.user_name = u.username;
      x.comic_name = c.name;
      x.con_tent = newComment.content;
      x.comment_time = newComment.commenttime;
      x._id = newComment._id;
    }
  }
  console.log(JSON.stringify(c));

  res.render('detail', { layout: 'main', comment: listComments, comic: convertleObject(c) })

}

const readComic = async (req, res)=>{
  const comicId = req.params.comicId;
  const c = await comic.findById(comicId);

  res.render('read', { layout: 'main', comic: convertleObject(c) })
}


module.exports = { getCommentbyIdComic, postComment, getDetailComicById, readComic };
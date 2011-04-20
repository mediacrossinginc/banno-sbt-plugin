import sbt._

object Git {
  def tag(tagName:String, msg: String, log: Logger): Option[String] = git("tag" :: "-a" :: "-m" :: msg :: tagName :: Nil, log)
  def commit(path: String, msg: String, log: Logger): Option[String] = git("add" :: path :: Nil, log) orElse git("commit" :: "-m" :: msg :: Nil, log)
  def push(log: Logger): Option[String] = None
  def pushTags(filter: String, log: Logger): Option[String] = None
  def isDifference(diffRevisions: String, log: Logger): Boolean = {
    val diff = Process("git" :: "diff" :: diffRevisions :: Nil) !! (log)
    !diff.isEmpty
  }

  private def git(args: List[String], log: Logger): Option[String] = {
    log.info("running git " + args.mkString(" "))
    val exitCode = Process("git" :: args) ! (log)
    if (exitCode == 0) {
      None
    } else {
      Some("Command 'git %s' failed with exit code %s".format(args.mkString(" "), exitCode))
    }
  }
}

package models

object Repo {
  def getRepos() : List[Repo] = List(Repo("lsug-dojo/lsug-website",
       "https://api.github.com/repos/lsug-dojo/lsug-website",
       "2012-10-27T09:50:36Z"))
    
}

case class Repo(fullName: String, url: String, createdAt: String) { }

USE [master]
GO
/****** Object:  Database [MARUNIFY]    Script Date: 24.12.2018 23:12:09 ******/
CREATE DATABASE [MARUNIFY]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'MARUNIFY', FILENAME = N'B:\ProgramFiles\MicrosoftSqlServer\MSSQL14.SQLEXPRESS2017\MSSQL\DATA\MARUNIFY.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'MARUNIFY_log', FILENAME = N'B:\ProgramFiles\MicrosoftSqlServer\MSSQL14.SQLEXPRESS2017\MSSQL\DATA\MARUNIFY_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [MARUNIFY] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [MARUNIFY].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [MARUNIFY] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [MARUNIFY] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [MARUNIFY] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [MARUNIFY] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [MARUNIFY] SET ARITHABORT OFF 
GO
ALTER DATABASE [MARUNIFY] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [MARUNIFY] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [MARUNIFY] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [MARUNIFY] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [MARUNIFY] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [MARUNIFY] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [MARUNIFY] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [MARUNIFY] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [MARUNIFY] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [MARUNIFY] SET  ENABLE_BROKER 
GO
ALTER DATABASE [MARUNIFY] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [MARUNIFY] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [MARUNIFY] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [MARUNIFY] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [MARUNIFY] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [MARUNIFY] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [MARUNIFY] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [MARUNIFY] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [MARUNIFY] SET  MULTI_USER 
GO
ALTER DATABASE [MARUNIFY] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [MARUNIFY] SET DB_CHAINING OFF 
GO
ALTER DATABASE [MARUNIFY] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [MARUNIFY] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [MARUNIFY] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [MARUNIFY] SET QUERY_STORE = OFF
GO
USE [MARUNIFY]
GO
/****** Object:  User [marunuser]    Script Date: 24.12.2018 23:12:09 ******/
CREATE USER [marunuser] WITHOUT LOGIN WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_accessadmin] ADD MEMBER [marunuser]
GO
/****** Object:  UserDefinedFunction [dbo].[GetTotalPlaylistDuration]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE FUNCTION [dbo].[GetTotalPlaylistDuration](@PlaylistName varchar(50))
RETURNS time
AS 
BEGIN
RETURN (SELECT CONVERT(TIME, DATEADD(s, SUM (( DATEPART(mi,s.duration) * 60) + (DATEPART(ss, s.duration))), 0)) 
FROM Song s, Playlist p, Playlist_Song ps
WHERE p.id = ps.playlistId
AND s.id = ps.songId
AND p.name = @PlaylistName)
END
GO
/****** Object:  Table [dbo].[Artist_T]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Artist_T](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NULL,
	[country] [varchar](50) NULL,
 CONSTRAINT [Artist_PK] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Album_T]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Album_T](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [varchar](50) NULL,
	[releaseDate] [date] NULL,
	[albumFormat] [varchar](1) NULL,
	[artistId] [int] NULL,
 CONSTRAINT [Album_PK] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Song]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Song](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [varchar](50) NULL,
	[duration] [time](7) NULL,
	[genreId] [int] NULL,
	[albumId] [int] NULL,
 CONSTRAINT [Song_PK] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[ReleasedLastEightYear]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[ReleasedLastEightYear] AS
SELECT s.title, al.title AS albumTitle, al.releaseDate, ar.name FROM Song s, Album_T al, Artist_T ar, 
(SELECT a.id, DATEDIFF(year,a.releaseDate, CAST(GETDATE() as DATE)) AS diffYear FROM Album_T a) as newTable 
WHERE newTable.diffYear < 8 
AND newTable.id = al.id
AND s.albumId = al.id
AND al.artistId = ar.id
GO
/****** Object:  Table [dbo].[User_T]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User_T](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](50) NOT NULL,
	[email] [varchar](50) NULL,
	[country] [varchar](50) NULL,
	[dateOfBirth] [date] NULL,
	[age]  AS (datediff(year,[dateOfBirth],CONVERT([date],getdate()))),
 CONSTRAINT [User_PK] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Playlist]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Playlist](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NULL,
	[userId] [int] NULL,
	[totalDuration] [time](7) NULL,
 CONSTRAINT [Playlist_PK] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Playlist_Song]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Playlist_Song](
	[playlistId] [int] NULL,
	[songId] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[UserBelovedSongs]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[UserBelovedSongs] AS
SELECT u.username, s.title, p.name  FROM Song s, Playlist p, Playlist_Song ps, User_T u 
WHERE u.id = p.userId
AND p.id = ps.playlistId
AND ps.songId = s.id 
GO
/****** Object:  View [dbo].[UserAndArtistFromSameCountry]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[UserAndArtistFromSameCountry] AS
SELECT u.username,  newTable.name, u.country  FROM User_T u, Artist_T as newTable 
where newTable.country = u.country 
GO
/****** Object:  View [dbo].[GetAllSongs]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[GetAllSongs] AS
SELECT s.title, s.duration, ar.name, ar.country FROM Song s, Artist_T ar, Album_T al
WHERE s.albumId = al.id
AND al.artistId = ar.id
GO
/****** Object:  View [dbo].[GetAllUsers]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[GetAllUsers] AS
SELECT u.username FROM User_T u
GO
/****** Object:  View [dbo].[GetPlaylistInfo]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[GetPlaylistInfo] AS
SELECT p.name, COUNT(s.id) as NumberOfSong FROM Playlist p, Playlist_Song ps, Song s
WHERE ps.playlistId = p.id
AND ps.songId = s.id
GROUP BY p.name
GO
/****** Object:  View [dbo].[GetAlbumInfo]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[GetAlbumInfo] AS
SELECT al.title, ar.name FROM Album_T al, Artist_T ar
WHERE al.artistId = ar.id
GO
/****** Object:  Table [dbo].[Genre]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Genre](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[title] [varchar](50) NULL,
 CONSTRAINT [Genre_PK] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[GetGenreInfo]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[GetGenreInfo] AS
SELECT g.title, COUNT(s.id) as NumberOfSong FROM Genre g, Song s
WHERE s.genreId = g.id
GROUP BY g.title
GO
/****** Object:  View [dbo].[GetArtistInfo]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[GetArtistInfo] AS
SELECT ar.name, COUNT(al.id) as NumberOfAlbum, COUNT(s.id) as NumberOfSong FROM Artist_T ar, Album_T al, Song s
WHERE ar.id = al.artistId
AND al.id = s.albumId
GROUP BY ar.name
GO
SET IDENTITY_INSERT [dbo].[Album_T] ON 
GO
INSERT [dbo].[Album_T] ([id], [title], [releaseDate], [albumFormat], [artistId]) VALUES (1, N'Ben', CAST(N'2010-12-31' AS Date), N'M', 3)
GO
INSERT [dbo].[Album_T] ([id], [title], [releaseDate], [albumFormat], [artistId]) VALUES (2, N'Rengarenk', CAST(N'2010-06-02' AS Date), N'M', 10)
GO
INSERT [dbo].[Album_T] ([id], [title], [releaseDate], [albumFormat], [artistId]) VALUES (3, N'Armut Velet', CAST(N'2012-01-15' AS Date), N'S', 2)
GO
INSERT [dbo].[Album_T] ([id], [title], [releaseDate], [albumFormat], [artistId]) VALUES (4, N'Ahde Vefa', CAST(N'2016-02-24' AS Date), N'M', 7)
GO
INSERT [dbo].[Album_T] ([id], [title], [releaseDate], [albumFormat], [artistId]) VALUES (5, N'Back to Black', CAST(N'2006-10-27' AS Date), N'M', 9)
GO
INSERT [dbo].[Album_T] ([id], [title], [releaseDate], [albumFormat], [artistId]) VALUES (6, N'Yalniz Turkuler', CAST(N'2018-12-03' AS Date), N'M', 1)
GO
SET IDENTITY_INSERT [dbo].[Album_T] OFF
GO
SET IDENTITY_INSERT [dbo].[Artist_T] ON 
GO
INSERT [dbo].[Artist_T] ([id], [name], [country]) VALUES (1, N'Sinan Kaynakci', N'İstanbul')
GO
INSERT [dbo].[Artist_T] ([id], [name], [country]) VALUES (2, N'Ada Bal', N'Hatay')
GO
INSERT [dbo].[Artist_T] ([id], [name], [country]) VALUES (3, N'Ogun Sanlisoy', N'Kocaeli')
GO
INSERT [dbo].[Artist_T] ([id], [name], [country]) VALUES (4, N'Kurt Cobain', N'Hoquiam')
GO
INSERT [dbo].[Artist_T] ([id], [name], [country]) VALUES (5, N'John Lennon', N'Liverpool')
GO
INSERT [dbo].[Artist_T] ([id], [name], [country]) VALUES (6, N'Metallica', N'California')
GO
INSERT [dbo].[Artist_T] ([id], [name], [country]) VALUES (7, N'Tarkan', N'Rize')
GO
INSERT [dbo].[Artist_T] ([id], [name], [country]) VALUES (8, N'Baris Akarsu', N'Bartin')
GO
INSERT [dbo].[Artist_T] ([id], [name], [country]) VALUES (9, N'Amy Winehouse', N'Londra')
GO
INSERT [dbo].[Artist_T] ([id], [name], [country]) VALUES (10, N'Sertap Erener', N'İstanbul')
GO
SET IDENTITY_INSERT [dbo].[Artist_T] OFF
GO
SET IDENTITY_INSERT [dbo].[Genre] ON 
GO
INSERT [dbo].[Genre] ([id], [title]) VALUES (1, N'Rock')
GO
INSERT [dbo].[Genre] ([id], [title]) VALUES (2, N'Pop')
GO
INSERT [dbo].[Genre] ([id], [title]) VALUES (3, N'Indie')
GO
INSERT [dbo].[Genre] ([id], [title]) VALUES (4, N'Rap')
GO
INSERT [dbo].[Genre] ([id], [title]) VALUES (5, N'Folk')
GO
INSERT [dbo].[Genre] ([id], [title]) VALUES (6, N'Metal')
GO
INSERT [dbo].[Genre] ([id], [title]) VALUES (7, N'Arabesque')
GO
INSERT [dbo].[Genre] ([id], [title]) VALUES (8, N'Jazz')
GO
SET IDENTITY_INSERT [dbo].[Genre] OFF
GO
SET IDENTITY_INSERT [dbo].[Playlist] ON 
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (1, N'MarunifyListem1', 3, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (2, N'IYI', 3, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (3, N'DarlingListem', 2, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (4, N'PubgGirl', 2, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (5, N'Kasap Selami', 4, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (6, N'Libadiye', 4, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (7, N'Enler', 7, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (8, N'Eses', 7, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (9, N'Soğukk', 5, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (10, N'Pipet', 5, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (11, N'ErasmusParty', 6, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (12, N'KozMaca', 6, NULL)
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (13, N'Onemli', 2, CAST(N'00:03:30' AS Time))
GO
INSERT [dbo].[Playlist] ([id], [name], [userId], [totalDuration]) VALUES (14, N'MahmutunPlaylisti', 10, CAST(N'00:03:34' AS Time))
GO
SET IDENTITY_INSERT [dbo].[Playlist] OFF
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (1, 4)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (1, 9)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (1, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (1, 13)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 3)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 6)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 7)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (3, 11)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (3, 12)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (3, 13)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (4, 1)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (4, 2)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (4, 4)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (5, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (5, 11)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (5, 5)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (5, 7)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (6, 3)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (6, 6)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (6, 8)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (6, 4)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (7, 2)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (7, 4)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (7, 5)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (7, 9)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (8, 9)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (8, 3)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (8, 6)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (9, 7)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (9, 4)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (9, 5)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (10, 3)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (10, 5)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (10, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (11, 12)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (11, 3)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (11, 4)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (12, 3)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (12, 5)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (12, 9)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (12, 13)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (2, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (13, 10)
GO
INSERT [dbo].[Playlist_Song] ([playlistId], [songId]) VALUES (14, 11)
GO
SET IDENTITY_INSERT [dbo].[Song] ON 
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (1, N'Ben', CAST(N'00:04:12' AS Time), 1, 1)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (2, N'Avunmak Zor', CAST(N'00:04:40' AS Time), 1, 1)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (3, N'Anma Arkadas', CAST(N'00:04:40' AS Time), 1, 1)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (4, N'Rengarenk', CAST(N'00:02:40' AS Time), 2, 2)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (5, N'Bir Varmisim Bir Yokmusum', CAST(N'00:03:16' AS Time), 2, 2)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (6, N'Koparilan Cicekler', CAST(N'00:04:24' AS Time), 2, 2)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (7, N'Armut Velet', CAST(N'00:03:16' AS Time), 3, 3)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (8, N'Rindlerin Aksami', CAST(N'00:05:08' AS Time), 5, 4)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (9, N'Olmaz İlac Sine-i Sad Pareme', CAST(N'00:03:23' AS Time), 5, 4)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (10, N'Soyleme Bilmesinler', CAST(N'00:03:30' AS Time), 5, 4)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (11, N'Rehab', CAST(N'00:03:34' AS Time), 8, 5)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (12, N'You Know I am No Good', CAST(N'00:04:17' AS Time), 8, 5)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (13, N'Me and Mr Jones', CAST(N'00:02:33' AS Time), 8, 5)
GO
INSERT [dbo].[Song] ([id], [title], [duration], [genreId], [albumId]) VALUES (15, N'Armut Velet Ikı', CAST(N'00:04:16' AS Time), 3, 3)
GO
SET IDENTITY_INSERT [dbo].[Song] OFF
GO
SET IDENTITY_INSERT [dbo].[User_T] ON 
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (1, N'adabal', N'adabal@gmail.com', N'Hatay', NULL)
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (2, N'biisra', N'büsrayagci@gmail.com', N'Ankara', NULL)
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (3, N'alperenbayar', N'alperenbayar@gmail.com', N'Elazıg', NULL)
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (4, N'sirmordred', N'bambubambu@gmail.com', N'Isparta', NULL)
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (5, N'sevdaadurdu', N'sevdadurdu@gmail.com', N'Gaziantep', NULL)
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (6, N'emrebarkinn', N'emrebarkinbozdag@gmail.com', N'Malatya', NULL)
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (7, N'nuriidyliz', N'nuriidyliz@gmail.com', N'Eskisehir', NULL)
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (8, N'i.mansiz', N'ilhanmansiz@gmail.com', N'Kempten', NULL)
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (9, N'oguzadiguzel', N'oguzadiguzel@gmail.com', N'Kütahya', NULL)
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (10, N'mahmutt', N'mahmutyildiz@gmail.com', N'İstanbul', NULL)
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (11, N'ibrahimm', N'iboaycan@gmail.com', N'Denizli', CAST(N'1997-10-10' AS Date))
GO
INSERT [dbo].[User_T] ([id], [username], [email], [country], [dateOfBirth]) VALUES (12, N'ibrahimm', N'iboaycan@gmail.com', N'Denizli', CAST(N'1997-10-10' AS Date))
GO
SET IDENTITY_INSERT [dbo].[User_T] OFF
GO
ALTER TABLE [dbo].[Album_T] ADD  CONSTRAINT [releaseDateDef]  DEFAULT (getdate()) FOR [releaseDate]
GO
ALTER TABLE [dbo].[Playlist] ADD  DEFAULT ('0:00:00') FOR [totalDuration]
GO
ALTER TABLE [dbo].[Album_T]  WITH CHECK ADD  CONSTRAINT [Artist_FK] FOREIGN KEY([artistId])
REFERENCES [dbo].[Artist_T] ([id])
GO
ALTER TABLE [dbo].[Album_T] CHECK CONSTRAINT [Artist_FK]
GO
ALTER TABLE [dbo].[Playlist]  WITH CHECK ADD  CONSTRAINT [User_FK] FOREIGN KEY([userId])
REFERENCES [dbo].[User_T] ([id])
GO
ALTER TABLE [dbo].[Playlist] CHECK CONSTRAINT [User_FK]
GO
ALTER TABLE [dbo].[Playlist_Song]  WITH CHECK ADD  CONSTRAINT [Playlist_Song_FK] FOREIGN KEY([playlistId])
REFERENCES [dbo].[Playlist] ([id])
GO
ALTER TABLE [dbo].[Playlist_Song] CHECK CONSTRAINT [Playlist_Song_FK]
GO
ALTER TABLE [dbo].[Playlist_Song]  WITH CHECK ADD  CONSTRAINT [Song_FK] FOREIGN KEY([songId])
REFERENCES [dbo].[Song] ([id])
GO
ALTER TABLE [dbo].[Playlist_Song] CHECK CONSTRAINT [Song_FK]
GO
ALTER TABLE [dbo].[Song]  WITH CHECK ADD  CONSTRAINT [Album_FK] FOREIGN KEY([albumId])
REFERENCES [dbo].[Album_T] ([id])
GO
ALTER TABLE [dbo].[Song] CHECK CONSTRAINT [Album_FK]
GO
ALTER TABLE [dbo].[Song]  WITH CHECK ADD  CONSTRAINT [Genre_FK] FOREIGN KEY([genreId])
REFERENCES [dbo].[Genre] ([id])
GO
ALTER TABLE [dbo].[Song] CHECK CONSTRAINT [Genre_FK]
GO
ALTER TABLE [dbo].[User_T]  WITH CHECK ADD CHECK  (([email] like '%.com'))
GO
/****** Object:  StoredProcedure [dbo].[GetInfoFromSong]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetInfoFromSong] 
	@Song varchar(50)
AS
	SELECT g.title, ar.name, al.title, s.duration FROM Artist_T ar, Album_T al, Song s, Genre g 
	WHERE s.albumId = al.id
	AND s.genreId = g.id
	AND al.artistId = ar.id
	AND s.title = @Song
GO
/****** Object:  StoredProcedure [dbo].[GetPlaylistFromUser]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetPlaylistFromUser]
	@UserName varchar(50)
AS
SELECT p.name FROM Playlist p, User_T u
WHERE @UserName = u.username
AND p.userId = u.id
GO
/****** Object:  StoredProcedure [dbo].[GetSongsFromAlbum]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetSongsFromAlbum]
	@AlbumName varchar(50)
AS
SELECT s.title, s.duration, ar.name, ar.country FROM Song s, Album_T al, Artist_T ar
WHERE @AlbumName = al.title
AND al.artistId = ar.id
AND s.albumId = al.id
GO
/****** Object:  StoredProcedure [dbo].[GetSongsFromArtist]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetSongsFromArtist]
	@ArtistName varchar(50)
AS
SELECT s.title, s.duration FROM SONG s, Artist_T ar, Album_T al
WHERE ar.name = @ArtistName
AND s.albumId = al.id
AND al.artistId = ar.id
GO
/****** Object:  StoredProcedure [dbo].[GetSongsFromGenre]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetSongsFromGenre]
	@Genre varchar(50)
AS
SELECT s.title, ar.name, s.duration FROM SONG s, Artist_T ar, Album_T al, Genre g
WHERE s.genreId = g.id 
AND g.title = @Genre
AND s.albumId = al.id
AND al.artistId = ar.id
GO
/****** Object:  StoredProcedure [dbo].[GetSongsFromPlaylist]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetSongsFromPlaylist]
	@Playlist varchar(50)
AS
SELECT s.title, s.duration, ar.name, ar.country FROM Song s, Album_T al, Artist_T ar, Playlist_Song ps, Playlist p
WHERE p.name = @Playlist
AND ps.playlistId = p.id
AND ps.songId = s.id
AND s.albumId = al.id
AND al.artistId = ar.id
GO
/****** Object:  StoredProcedure [dbo].[GetTotalDurationOfAllSongs]    Script Date: 24.12.2018 23:12:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[GetTotalDurationOfAllSongs] AS
SELECT CONVERT(TIME, DATEADD(s, SUM (( DATEPART(mi,duration) * 60) + (DATEPART(ss, duration))), 0)) AS TotalDuration
FROM Song

GO
USE [master]
GO
ALTER DATABASE [MARUNIFY] SET  READ_WRITE 
GO

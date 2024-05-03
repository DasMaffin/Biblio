package com.example.Biblio.Model;

public enum SubjectGroup
{
    // Define enum constants for each subject group
    // https://www.dnb.de/SharedDocs/Downloads/DE/Professionell/DDC/ddcSachgruppenDNBAb2013.pdf?__blob=publicationFile&v=7
    Generalities(0, "Allgemeines, Informatik, Informationswissenschaft"),
    ComputerScience(4, "Informatik"),
    Bibliographies(10, "Bibliografien"),
    LibraryScience(20, "Bibliotheks- und Informationswissenschaft"),
    Encyclopedias(30, "Enzyklopädien"),
    Journals(50, "Zeitschriften, fortlaufende Sammelwerke"),
    Organizations(60, "Organisationen, Museumswissenschaft"),
    Journalism(70, "Nachrichtenmedien, Journalismus, Verlagswesen"),
    GeneralWorks(80, "Allgemeine Sammelwerke"),
    RareBooks(90, "Handschriften, seltene Bücher"),
    PhilosophyPsychology(100, "Philosophie und Psychologie"),
    Philosophy(100, "Philosophie"),
    ParapsychologyOccultism(130, "Parapsychologie, Okkultismus"),
    Psychology(150, "Psychologie"),
    Religion(200, "Religion"),
    Bible(220, "Bibel"),
    Theology(230, "Theologie, Christentum"),
    OtherReligions(290, "Andere Religionen"),
    SocialSciences(300, "Sozialwissenschaften"),
    SociologyAnthropology(300, "Sozialwissenschaften, Soziologie, Anthropologie"),
    Statistics(310, "Allgemeine Statistiken"),
    Politics(320, "Politik"),
    Economics(330, "Wirtschaft"),
    NaturalResources(3337, "Natürliche Ressourcen, Energie und Umwelt"),
    Law(340, "Recht"),
    PublicAdministration(350, "Öffentliche Verwaltung"),
    MilitaryScience(355, "Militär"),
    SocialProblemsServices(360, "Soziale Probleme, Sozialdienste, Versicherungen"),
    Education(370, "Erziehung, Schul- und Bildungswesen"),
    CommerceCommunicationsTransportation(380, "Handel, Kommunikation, Verkehr"),
    CustomsEtiquetteFolklore(390, "Bräuche, Etikette, Folklore"),
    Language(400, "Sprache"),
    Linguistics(400, "Sprache, Linguistik"),
    English(420, "Englisch"),
    German(430, "Deutsch"),
    OtherGermanicLanguages(439, "Andere germanische Sprachen"),
    French(440, "Französisch, romanische Sprachen allgemein"),
    Italian(450, "Italienisch, Rumänisch, Rätoromanisch"),
    Spanish(460, "Spanisch, Portugiesisch"),
    Latin(470, "Latein"),
    Greek(480, "Griechisch"),
    OtherLanguages(490, "Andere Sprachen"),
    SlavicLanguages(4918, "Slawische Sprachen"),
    NaturalSciencesMathematics(500, "Naturwissenschaften und Mathematik"),
    NaturalSciences(500, "Naturwissenschaften"),
    Mathematics(510, "Mathematik"),
    AstronomyCartography(520, "Astronomie, Kartografie"),
    Physics(530, "Physik"),
    Chemistry(540, "Chemie"),
    Geosciences(550, "Geowissenschaften"),
    Paleontology(560, "Paläontologie"),
    LifeSciencesBiology(570, "Biowissenschaften, Biologie"),
    PlantsBotany(580, "Pflanzen (Botanik)"),
    AnimalsZoology(590, "Tiere (Zoologie)"),
    EngineeringMedicineAppliedSciences(600, "Technik, Medizin, angewandte Wissenschaften"),
    Engineering(600, "Technik"),
    MedicineHealth(610, "Medizin, Gesundheit"),
    EngineeringMechanicalEngineering(620, "Ingenieurwissenschaften und Maschinenbau"),
    ElectricalEngineeringElectronics(6213, "Elektrotechnik, Elektronik"),
    CivilEnvironmentalEngineering(624, "Ingenieurbau und Umwelttechnik"),
    AgricultureVeterinaryMedicine(630, "Landwirtschaft, Veterinärmedizin"),
    HomeEconomicsFamilyLiving(640, "Hauswirtschaft und Familienleben"),
    Management(650, "Management"),
    TechnicalChemistry(660, "Technische Chemie"),
    IndustrialHandicrafts(670, "Industrielle und handwerkliche Fertigung"),
    BuildingConstruction(690, "Hausbau, Bauhandwerk"),
    ArtsEntertainment(700, "Künste und Unterhaltung"),
    VisualArts(700, "Künste, Bildende Kunst allgemein"),
    LandscapeArchitecture(710, "Landschaftsgestaltung, Raumplanung"),
    Architecture(720, "Architektur"),
    Sculpture(730, "Plastik, Numismatik, Keramik, Metallkunst"),
    GraphicArts(740, "Grafik, angewandte Kunst"),
    ComicsCartoons(7415, "Comics, Cartoons, Karikaturen"),
    Painting(750, "Malerei"),
    Printmaking(760, "Druckgrafik, Drucke"),
    Photography(770, "Fotografie, Video, Computerkunst"),
    Music(780, "Musik"),
    RecreationPerformingArts(790, "Freizeitgestaltung, Darstellende Kunst"),
    PublicPerformancesFilmBroadcasting(791, "Öffentliche Darbietungen, Film, Rundfunk"),
    TheaterDance(792, "Theater, Tanz"),
    Games(793, "Spiel"),
    Sports(796, "Sport"),
    Literature(800, "Literatur"),
    LiteraryCriticism(800, "Literatur, Rhetorik, Literaturwissenschaft"),
    AmericanLiterature(810, "Englische Literatur Amerikas"),
    EnglishLiterature(820, "Englische Literatur"),
    GermanLiterature(830, "Deutsche Literatur"),
    OtherGermanicLiterature(839, "Literatur in anderen germanischen Sprachen"),
    FrenchLiterature(840, "Französische Literatur"),
    ItalianLiterature(850, "Italienische, rumänische, rätoromanische Literatur"),
    SpanishPortugueseLiterature(860, "Spanische und portugiesische Literatur"),
    LatinLiterature(870, "Lateinische Literatur"),
    GreekLiterature(880, "Griechische Literatur"),
    OtherLiterature(890, "Literatur in anderen Sprachen"),
    SlavicLiterature(8918, "Slawische Literatur"),
    HistoryGeography(900, "Geschichte und Geografie"),
    History(900, "Geschichte"),
    GeographyTravel(910, "Geografie, Reisen"),
    GeographyGermany(9143, "Geografie, Reisen (Deutschland)"),
    BiographyGenealogyHeraldry(920, "Biografie, Genealogie, Heraldik"),
    AncientHistoryArchaeology(930, "Alte Geschichte, Archäologie"),
    EuropeanHistory(940, "Geschichte Europas"),
    GermanHistory(943, "Geschichte Deutschlands"),
    AsianHistory(950, "Geschichte Asiens"),
    AfricanHistory(960, "Geschichte Afrikas"),
    NorthAmericanHistory(970, "Geschichte Nordamerikas"),
    SouthAmericanHistory(980, "Geschichte Südamerikas"),
    WorldHistory(990, "Geschichte der übrigen Welt"),
    Fiction(1000, "Belletristik"),
    ChildrensLiterature(1010, "K Kinder- und Jugendliteratur"),
    Textbooks(1020, "S Schulbücher");

    // Instance variable to store the label of the subject group
    private final String label;
    private final int groupNumber;

    // Constructor
    SubjectGroup(int num, String label) {
        this.groupNumber = num;
        this.label = label;
    }

    // Getter method
    public String getLabel() {
        return label;
    }

    public int getGroupNumber(){
        return groupNumber;
    }

    public static SubjectGroup fromString(String stringValue) {
        for (SubjectGroup group : SubjectGroup.values()) {
            if (group.label.equals(stringValue)) {
                return group;
            }
        }
        return null; // Or throw an exception if the input string doesn't match any enum constant
    }
}

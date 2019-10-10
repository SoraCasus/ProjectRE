package com.soracasus.projectre;

import com.soracasus.projectre.core.IGameLogic;
import com.soracasus.projectre.core.MouseInput;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.StateManager;
import com.soracasus.projectre.core.Window;
import com.soracasus.projectre.render.Camera;
import com.soracasus.projectre.render.entity.Rock1;
import com.soracasus.projectre.render.renderers.SkyboxRenderer;
import com.soracasus.projectre.render.skybox.Skybox;
import com.soracasus.projectre.render.texture.Texture;
import com.soracasus.projectre.render.ui.Colour;
import com.soracasus.projectre.render.ui.IUIObject;
import com.soracasus.projectre.render.ui.UIBox;
import com.soracasus.projectre.render.ui.UIButton;
import com.soracasus.projectre.render.ui.UIManager;
import com.soracasus.projectre.render.ui.UITextBox;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class DocumentState implements IGameLogic {

	private boolean initialized = false;

	private SkyboxRenderer skyboxRenderer;
	private Camera camera;
	private Skybox skybox;

	private List<IUIObject> uiElements;

	@Override
	public void init (Window window) {
		skyboxRenderer = new SkyboxRenderer(window);

		UIManager.init(window);

		uiElements = new ArrayList<>();

		Rock1 r = new Rock1(new Vector3f());
		camera = new Camera(r, 0);
		camera.setMenuCam(true);


		REFile[] skyTextures = {
				new REFile("skybox/red/bkg2_right1.png"),
				new REFile("skybox/red/bkg2_left2.png"),
				new REFile("skybox/red/bkg2_top3.png"),
				new REFile("skybox/red/bkg2_bottom4.png"),
				new REFile("skybox/red/bkg2_front5.png"),
				new REFile("skybox/red/bkg2_back6.png"),
		};

		Texture skyTexture = Texture.newCubeMap(skyTextures);

		skybox = new Skybox(skyTexture, 100);

		UIBox box = new UIBox(new Vector2f(-0.5F, -0.8F), new Vector2f(640.0f, 670),
				new Colour(0.5F, 0.5F, 0.5F, 0.5F));
		uiElements.add(box);

		UITextBox text = new UITextBox(new Vector2f(-0.49F, -0.75F), 20F, 635.0F,
				new Colour(0.0F, 0.0F, 0.0F, 1.0F), NanoVG.NVG_ALIGN_LEFT, "");
		uiElements.add(text);

		UIBox overviewBox = new UIBox(new Vector2f(-0.95F, -0.95F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton overviewBtn = new UIButton(overviewBox, "Overview", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"The Rutikal come from the planet they have named Rut, which in their language means “Luck.” While most refer to it simply as their “home” or “home planet” asking them what planet they came from will get you the answer Rut, even from those born off world. To them Rut is their home, even if they weren't born their. \n" +
						"The planet itself is lush with plant and animal life evolved to survive rain that hits harder than literal bullets, often times either taking refuge in ruins left behind by the precursor sentient species that can be found all over the planet in high quantities, or in caves or dens that have been dug to keep the water out. Thankfully these rains are infrequent, happening only once a month in the wet parts of the planet and only once every several years in some deserts, the longest time recorded without one being 3 years in a small desert and city located in it. While normal rain is still present and much more common than the “bullet rain storm” as it's been named, Rutikal will often keep an eye out for the deadly rains. Most Rutikal cities have a surface level and underground level, both which are equally spacious underground as they are on the surface.\n" +
						"The Rutikal are considered to be slightly religious, the most popular religion being one similar to Buddhism. While the term slightly religious is used to describe them, most would not consider themselves very religious or religious at all. Their culture loosely their religion in a way that outsiders may still indulge in festivals and still see elements of it when they throw them twice a year on their home planet and cities where Rutikal make up the majority of the population.\n"
		));
		uiElements.add(overviewBtn);

		UIBox appearanceOneBox = new UIBox(new Vector2f(-0.95F, -0.80F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton appearanceOneBtn = new UIButton(appearanceOneBox, "Appearance Pt. 1", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"To them an outfit like the one seen in the image earlier is completely normal (spare the face wraps), though some will wear even more clothing, including things like ponchos, dusters, hooded jackets, and vests, or shorts and a t-shirt. Ponchos and dusters are currently the most popular choices for extra clothing. Their clothes will also come in all sort of different colors much like human clothing can, and will also often times have designs of some sorts on them. While Rutikals wear whatever fits their preferences best, pants and long sleeve shirts tend to be more popular than normal t-shirts and shorts. Pant style can also vary as well, but usually it's how many pockets are on the pants that will change. Some may also opt for other kinds of hats, ranging from baseball caps to sun hats or even not wearing hat at all, their bamboo styled had remains the most popular as it keeps them the driest when it rains (in their opinion). Shoes are optional and purely based on personal preference if one wears them or not.\n" +
						"It is considered to be borderline nudity to most Rutikal (except those which were raised in a different culture) to wear shorts and no shirt as that's usually an “at home” outfit. Most will find themselves embarrassed if they are caught outside in an outfit they would only wear at home as they would usually only dress that way if they are staying at home or going to bed, and most would be embarrassed to see someone dressed as to be in their own home much like a human would be when they see someone in nothing but their underwear.\n"
		));
		uiElements.add(appearanceOneBtn);

		UIBox appearanceTwoBox = new UIBox(new Vector2f(-0.95F, -0.65F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton appearanceTwoBtn = new UIButton(appearanceTwoBox, "Appearance Pt. 2", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"Most Rutikal are thin, and they tend to be an active species so finding one with more than a slight amount of belly chub is rare. Males stand around 6’3 on average, while females are normally 5’10 and a half to 6 feet depending on where they are from on average. Their tails are generally long and start out thick, getting thinner as they get closer to the tip of the tail. Most, with the exception of those with genes changing their eye color, have slightly large, black eyes. Their faces tend to be look well rounded due to the large amounts of fluff around their cheeks and chin. If the fluff was trimmed to be very short their faces would look thin. The fur on their arms are short but soft looking. While mostly white it's common to find various colored markings on their face and arms. Their arms often times look as though they have been dipped into the color up to about halfway on their forearms, or have had them splashed on. The ones on their face tend to be lines or swirls, near the upper part of the face and eyes. These colors can greatly vary and can be anything besides neon colors naturally."
		));
		uiElements.add(appearanceTwoBtn);

		UIBox biologyOneBox = new UIBox(new Vector2f(-0.95F, -0.50F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton biologyOneBtn = new UIButton(biologyOneBox, "Biology Pt. 1", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"Rutikals are naturally thin, and as mentioned above males are generally a good 3 to 5 inches taller than females and stand at 6’3 on average. Females do have breasts, however they are normally small. Their fur color is always white but can be dyed permanently for their own version of tatoos. Their bodies also have a metabolism faster than that of a human's, so they are usually thin. Their grip and arms are extremely strong as they have evolved to be able to climb and pull themselves to safety and away from threats in the ruins that originally covered the world before they began starting civilizations. They are naturally dexterous due to evolving to climb up poles, walls, and even through pipes both vertically and horizontally. Their brains have also evolved to help them navigate complex areas, leading them to becoming excellent cartographers and navigators. They can effectively close their eyes and visualize a route to their destination in great detail if they have been to said destination at least once and know enough of the area around and between them and where they are going. Their eyes are slightly sensitive to bright lights as they have become more accustomed to spending more time underground than before and oftentimes wear hats. They are tolerant to heat as they originate from a humid planet, however still need to bundle up for colder places. They do need to exercise consistently as it's easy for them to them to fall from a good physical condition, leading to a larger amount of sleep required to feel completely rested or getting exhausted much easier."
		));
		uiElements.add(biologyOneBtn);

		UIBox biologyTwoBox = new UIBox(new Vector2f(-0.95F, -0.35F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton biologyTwoBtn = new UIButton(biologyTwoBox, "Biology Pt. 2", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"Rutikals have several rare genes exhibited in them, including having 4 ears instead of 2, different colored eyes instead of the normal solid black, and stronger than normal, prehensile tails being the most notable rare genes. \n" +
						"Rutikals which posses the rare, 4 eared trait have the ability to locate the origins of a sound much better than those with only 2, and with training can use echolocation to navigate in the event they have either gone blind or are in complete darkness. They can move all 4 of their ears around as well as those with only 2. This trait can be passed along to offspring as it's not the result of a mutation, however does not always get passed along. This gene is much more common in males than it is in females. \n" +
						"Those that show the colored eyes gene often have darker colors ranging from blue or green to even purple for their eyes. About half of those with this gene have poor eyesight which can either be corrected with surgery or glasses. This gene is a mutation and generally can not be passed along to offspring with exception of those with the caver ethnicity, which is the most common cause for colored eyes.\n" +
						"Rutikal that possess naturally prehensile tails can hold themselves up with just their tail as they are much stronger than a normal Rutikals’. They can use it to assist themselves when climbing, hold or use items, or perform tasks that require fine motor skills with training. This gene can be passed along to children, however it's common for Rutikal that don't naturally have a prehensile tail to get surgery to have bionic muscles installed into their tail to effectively give them the artificial equivalent of a naturally prehensile tail. Due to selective breeding programs the prehensile tail is a much more common trait than it was before the Rutikal began exploring space.\n"
		));
		uiElements.add(biologyTwoBtn);

		UIBox biologyThreeBox = new UIBox(new Vector2f(-0.95F, -0.20F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton biologyThreeBtn = new UIButton(biologyThreeBox, "Biology Pt. 3", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"Different ethnicities for the Rutikal exist; while no formal names for them have been translated yet, roughly translated and general descriptive names are currently acceptable. These ethnicities often affect the size, some facial features, and shapes of some body parts. The most commonly seen ethnicities are the cold dwellers, dune walkers, Cavers, and Tuck. Those of the Cold Dweller ethnicity tend to be shorter, have thicker fur, shortened snouts, and thicker tails and body shape. Dune Walkers tend to be tall, have larger ears, thinner slightly darker hair, narrower eyes, and longer tails. The Caver ethnicity tend to have broader ears, slightly larger eyes, slightly below average height, various shades of lighter green eyes, and thinner fingers. Something to note about the Caver ethnicity is that it is the only ethnicity to possess a gene changing eye color that can be passed along to children, however they tend to be nearsighted because of it. The most common Rutikal ethnicity is the Tuck, aka the average Rutikal. For the most part a Rutikal of the Tuck ethnicity can be described in average in height, weight, and body shape, they do however tend to have slightly smaller feet than most other ethnicities."
		));
		uiElements.add(biologyThreeBtn);

		UIBox cultureOneBox = new UIBox(new Vector2f(-0.95F, -0.05F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton cultureOneBtn = new UIButton(cultureOneBox, "Culture Pt. 1", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"The Rutikal have a semi complex culture, sharing some similarities to Japanese culture when it comes to building design and architecture. They value trust, knowledge, and respect the most in society. Knowledge and the willingness to learn is valued as well, as they are a naturally curious species which desires to learn more. While they won't dislike someone for being stupid, they will dislike someone for being ignorant and unwilling to learn something new, no matter if the teacher is someone younger, older, or even a different species. To them a teacher is still a teacher. Respect is the final of the 3 things they value socially. Disrespect is usually seen as a sign of ignorance and a belief of superiority over others. While people aren't required to like or even be very nice to someone, they are still expected to respect them. While insulting someone is obviously considered disrespectful, it's also frowned upon to be overly sensitive towards others opinions of them. They also believe that people should treat others the way they expect to be treated, and that good people always get good karma in return due to their Buddhist like religion’s influence on their culture"
		));
		uiElements.add(cultureOneBtn);

		UIBox cultureTwoBox = new UIBox(new Vector2f(-0.95F, 0.10F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton cultureTwoBtn = new UIButton(cultureTwoBox, "Culture Pt. 2", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"Festivals is where their Buddhist like religion is the most involved in their culture. While it's shown mostly through symbolism on jewelry or art pieces, festivals are generally held twice a year as a celebration of life and good deeds. Most use it as an excuse to go out, party for several days, dance all night, shop at stalls that will line the streets, and have a good time. Note that this is a general description of their culture, and many different subcultures exist in their society.\n" +
						"The most popular sport they have that does not involve zero gravity is one where they set up complex, metal pipe structures and have to climb/jump around with a ball as they attempt to throw it into the other teams goal. Letting the ball fall and hit the floor will result in it going to the other team as the goal is to keep the ball off the floor as you get it into the other teams net. Their most popular sport involves zero gravity, paintball guns, and flash drives. The goal of the game is to try and find the other teams hidden “flag” and simply plug the flashdrive into a port. The paintballs contain a special chemical in it that when a player's suit is struck by the paint inside, the limb shot (or entire body if it's the torso or helmet that is shot) will freeze up completely depending on how much “armor” the player has (a players armor will dictate not only how many times they can be shot, but will also reduce their mobility) until washed off with water. The sport is played by Rutikal of all ages and is quite popular.\n"
		));
		uiElements.add(cultureTwoBtn);

		UIBox cultureThreeBox = new UIBox(new Vector2f(-0.95F, 0.25F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton cultureThreeBtn = new UIButton(cultureThreeBox, "Culture Pt. 3", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"Families are considered very important to them, and while a father and mother might not like each other or be married at all, they will still live with each other until all their children have grown up and moved out. Mothers will often teach the children the more formal parts of society, the fathers are generally more involved with teaching and raising the children in general, promoting both improvisation skills and self defense skills. It's extremely common to see a father and his children climbing and exploring ruins found outside their city, either alone or in a group of fathers and children. Most children are born individually, though twins can happen and families usually have 2 to 3 children on average. Children who are orphaned are generally adopted quickly as the Rutikal (more often than not they are adopted by one parent or same sex couples) see their children as their future, and wish to try and ensure their future will be the best possible. Rutikals normally are considered adults and able to leave for either college or to live on their own at around age 20\n"
		));
		uiElements.add(cultureThreeBtn);


		UIBox cultureFourBox = new UIBox(new Vector2f(-0.95F, 0.40F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton cultureFourBtn = new UIButton(cultureFourBox, "Culture Pt. 4", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"Due to the lack of differences at a glance between most Rutikal ethnicities, the role it plays on the Rutikal culture is often clumped together with where the family originated from.\n" +
						"While the Rutikal are democratic, their emperor is their elected leader making the title emperor purely a title. Once an emperor is elected, he or she will lead for a 40 year period. The Rutikal will also vote on representatives, creating a body of representatives reaching the number of 401 people (odd to prevent ties) who will watch the emperor to ensure that they are remaining fair and just, check over laws and decisions made by the emperor and denying those they decide against, and monitoring the judicial system for the emperor. They will also choose a replacement for the emperor in 3 cases, they needs a temporary break to prevent stress from impairing their decisions, they are too ill to work, or they have died.\n"
		));
		uiElements.add(cultureFourBtn);

		UIBox littlePuppetBox = new UIBox(new Vector2f(-0.95F, 0.55F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton littlePuppetBtn = new UIButton(littlePuppetBox, "Little Puppet", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"Little puppets are cloth dolls made to look like a dead Rutikal. Oftentimes they take a similar appearance to what the dead looked like as a child. They are believed to have returned with important information, however are unable to speak or make noise in anyway. Due to their inability to speak, most will adapt to a mischievous way of behaving, moving things around and sometimes even setting up small “pranks” to attempt to try and get others to realize/remember the important information. Usually they are seen as a small reward of good karma, and often leave once the information is conveyed."
		));
		uiElements.add(littlePuppetBtn);

		UIBox lurkingShadowsBox = new UIBox(new Vector2f(-0.95F, 0.70F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton lurkingShadowsBtn = new UIButton(lurkingShadowsBox, "Lurking Shadows", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"Lurking shadows are large, black humanoid creatures with two heads and wings. One head will always have two Rutikal like ears while the other will always have four. Their wings are feathery. Seen by children in fields of crops where they play (often times unwelcomed by the farmer), lurking shadows are seen as protectors of crops. When not lurking around the field they are believed to be sleeping in scarecrows put up by farmers. The nicer the scarecrow the bigger they are. If a farmer does not take care of the scarecrow, lurking shadows will become spiteful and will throw things at the farmer in the field until the scarecrow is attended to."
		));
		uiElements.add(lurkingShadowsBtn);

		UIBox precursorSpeciesBox = new UIBox(new Vector2f(0.65F, -0.95F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton precursorSpeciesBtn = new UIButton(precursorSpeciesBox, "Precursor Species", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"The Rutikal are the second sentient species on their planet, however the first species did not get past the atomic age due to an illness that killed them off by causing permanent sterilization of those who survived the disease with a 50% mortality rate in adults and a 100% mortality rate in children. This caused the precursors to die off as a species as they could not have any more children. By the time the Rutikal could properly understand the technology of the precursors and translate their language consistently, they had already matched the same technological level as the precursors. 3 of the top 10 most popular books on the Rutikal homeworld originally were written by the precursors, including a detailed description of what life alone with few survivors scattered across the world, either fighting, working together with others, or flat out avoiding each other, was like for the last remaining precursor who was able to confirm that the he was indeed the last precursor when he began his book. While it does not contain any descriptions of what the precursors looked like aside from  as the last survivor was an author who liked to leave a little bit of mystery about those he wrote about, it did teach the Rutikal about the last moments of the precursor kind and proved to be a very popular read to them. The most famous part of the book was when the author finished off by mentioning that he believed either a new sentient species would rise, or aliens would come across the planet currently in ruins and stumble across his book. The book was later made into a movie once animation became advanced enough to allow for the entire film to be animated and still look life like and be accurate to how the world was described before the precursor extinction and how the precursors looked themselves"
		));
		uiElements.add(precursorSpeciesBtn);

		UIBox technologyOneBox = new UIBox(new Vector2f(0.65F, -0.80F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton technologyOneBtn = new UIButton(technologyOneBox, "Technology Pt. 1", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"They are currently in the late space age early advanced space age (Inhabiting multiple planets as the dominate species and living on planets with other sentient species). Bionics are something they have virtually perfected, and while most are only offered to either those in the military or who have lost limbs, bionic muscles for the hands to increase fine motor skills the tail to make it prehensile are publicly available. Bionic replacements or bionic muscle replacements to increase agility also publicly available. While bionic ears can be installed to change a Rutikals number of ears from 2 to 4, it's rare for it to be legally available to the public or non specialist members of the military, having them installed will not result in any fines or legal punishment as they often times take up to a year to get used to having.\n" +
						"Their land vehicles are often designed to be able to handle all terrains at high speeds. Personal civilians vehicles are usually either trucks with advanced suspension systems allowing for smooth rides over rocky and difficult terrain at high speeds, or motorcycles that advanced versions of enduro dirt bikes. While some do use cars, trucks and bikes are the most popular on their home world due to ease of traversing the generally rough terrain.\n"
		));
		uiElements.add(technologyOneBtn);

		UIBox technologyTwoBox = new UIBox(new Vector2f(0.65F, -0.65F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton technologyTwoBtn = new UIButton(technologyTwoBox, "Technology Pt. 2", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"Their spacecraft are generally lighter and sleeker in design appearance, however are not under armored. Military craft tend to be extremely heavily armored in combat situations and designed to be able to move around the same speed as other, less armored spacecraft, to where they need to be before the actual battle begins, using either force fields or movable internal armor plates between what is effectively the outer and inner hull. Military ships with the exclusion of stealth, scout, and ships designed to effectively serve as snipers against small and medium hostile ships, are typically larger than their counterparts from other species.\n" +
						"Ground forces are often equipped with moderate to heavily armored exo suits, allowing both protection from enemy fire and increase mobility and strength on the battlefield. Ground units are generally trained to be able to take several roles in case one needs to be filled (such as the designated medic being shot and needing medical attention). On the battle field they value being able to win through superior training and gear over numbers.\n" +
						"\n" +
						"They have developed sentient AIs with the intelligence equivalent of someone who is around the age of 18. They generally are in mobile bodies and are around the size of someone who would be 13 (they find them being small is kinda cute) and are humanoid in shape. AIs are oftentimes used as assistance, company at home, or research (which oftentimes requires many upgrades to the AI) The most popular model is grey in color with an LED screen for a face and 4 silver “wing like ears” on the sides of their heads.\n"
		));
		uiElements.add(technologyTwoBtn);

		UIBox diplomacyBox = new UIBox(new Vector2f(0.65F, -0.65F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton diplomacyBtn = new UIButton(diplomacyBox, "Diplomacy", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"While generally peaceful, they will not tolerate acts of hostility from other species if they have recently met. They do enjoy and celebrate cooperation with other species, however will approach with caution. They are extremely formal during meetings and often times make an effort to send a diplomat who either understands or can speak the other's language. Species that currently have some hostilities with the Rutikal often times end up banned from entering space or areas claimed by the Rutikal, depending on if their Emperor decides that either the entire species is a threat or purely their government. If the emperor decides that it's just the government that is a threat, civilians are allowed to enter as long as they do not hold any government job or are map makers. Friendly species will often times get trade offers from or invited to partake in projects with the Rutikals, along with aid from them in times of need. Those that are neutral with them will get aid in times of natural disasters and occasion trade offers in attempts to try and create a warmer diplomatic relationship. Those that are hostile will get little to no effort to end hostilities/tension from the Rutikal until they show that they are willing to put in a fair effort into ending the hostilities/tension. While the Rutikal are Pseudo democratic, they will only ever get concerned about another species convernment if it is communistic or the general population of said species is suffering greatly.\n" +
						"Technology is rarely shared between the Rutikal and other species unless they have a very warm and close diplomatic relationship, this generally includes announcing that the two species are now allies.\n"
		));
		uiElements.add(diplomacyBtn);

		UIBox humanBox = new UIBox(new Vector2f(0.65F, -0.50F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton humanBtn = new UIButton(humanBox, "Human Relations", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"They hold a friendly stance towards the humans, open to trade and joined projects with them. While technology sharing is only semi common between the two, the Rutikal believe they get along very well with the humans. They find normal humans to be silly sometimes, but respectable in most manners."
		));
		uiElements.add(humanBtn);

		UIBox wrongdoingsOneBox = new UIBox(new Vector2f(0.65F, -0.35F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton wrongdoingsOneBtn = new UIButton(wrongdoingsOneBox, "Wrong Doings Pt. 1", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				"The Rutikal aren't a race that has done nothing but good. They have committed acts against both themselves and against others that they would like to forget and move on from. There are also extremist groups which remain from the Blen movement who will sometimes perform terrorist attacks on cities with large amounts of aliens or on aliens planets.\n" +
						"The Blen genocide. A xenophobic movement known as “The Blen” which promoted the idea that the Rutikal were superior to other sentient species. The movement originated from one of the smaller but well developed Rutikal planets. The planet split off from the Rutikal empire and began capturing members of the other sentient species. Those that were captured were either forced into labor or subject to experiments involving changing prisoners into Rutikals. Most failed horrible resulting in death and those that did survive were kept as prisoners. After the aggressive attacks on the other species the Rutikal empire attacked the planet killing most living on it including civilians during the process. This is the most recent large scale wrongdoing.\n"
		));
		uiElements.add(wrongdoingsOneBtn);

		UIBox wrongdoingsTwoBox = new UIBox(new Vector2f(0.65F, -0.20F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton wrongdoingsTwoBtn = new UIButton(wrongdoingsTwoBox, "Wrong Doings Pt. 2", new Colour(0.0F, 0.0F, 0.0F), () -> text.setText(
				" During the earlier stages of their colonization efforts the Rutikal stumbled across a hunter gatherer sentient species. The species responded with aggression due to confusion as to what the Rutikal were and the beginning of experimental terraforming process (which intruded into areas where they were found) which caused them to be mistaken as extremely aggressive. The species turned out to be much friendlier than originally thought when approached by single individuals instead of small groups but not until nearly being driven to extinction. After that point the Rutikal left the planet and monitored it, making sure that the sentient species would recover from the near extinction and change in environment. Several native flora and fauna species went extinct in the process and the Rutikal refuse to disclose the location of the planet, nor will they name the species.\n" +
						"Historians are currently looking into archives for more history on previous wars, relations, and other notable events.\n"
		));
		uiElements.add(wrongdoingsTwoBtn);

		UIBox backBox = new UIBox(new Vector2f(0.65F, 0.70F), new Vector2f(200, 50), new Colour(0.78F, 0.0F, 0.22F));
		UIButton backBtn = new UIButton(backBox, "Back", new Colour(0.0F, 0.0F, 0.0F), () -> StateManager.setState(StateManager.menuState));
		uiElements.add(backBtn);


		initialized = true;
	}

	@Override
	public void input (Window window, MouseInput mouseInput) {
		camera.input(window, mouseInput);

		for (IUIObject o : uiElements)
			o.input(window, mouseInput);
	}

	@Override
	public void render (Window window) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

		skyboxRenderer.render(skybox, camera);

		UIManager.render(window, uiElements);
	}

	@Override
	public void update (float dt) {
		camera.update(dt);
	}

	@Override
	public boolean initialized () {
		return initialized;
	}
}

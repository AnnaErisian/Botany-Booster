package blue.thejester.botanybooster.asm;

import blue.thejester.botanybooster.asm.names.ObfuscatedName;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;
import org.objectweb.asm.Label;

public class ClassTransformer implements IClassTransformer
{
	public static Logger logger = LogManager.getLogger("BotanyBoosterCore");

	final String asmHandler = "blue/thejester/botanybooster/asm/handler/AsmHandler";

	public static int transformations = 0;

	public ClassTransformer()
	{
		logger.log(Level.DEBUG, "Starting Class Transformation");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{

		logger.log(Level.DEBUG, "Attempting Transform from Botany Booster");
//		if (transformedName.equals("net.minecraft.entity.EntityLivingBase"))
//		{
//			transformations++;
//			return patchEntityLivingBase(basicClass);
//		}
		if (transformedName.equals("net.minecraft.world.World"))
		{
			transformations++;
			return patchWorldClass(basicClass);
		}
		else if (transformedName.equals("net.minecraft.client.renderer.EntityRenderer"))
		{
			transformations++;
			return patchEntityRenderer(basicClass);
		}
		else if (transformedName.equals("vazkii.botania.common.entity.EntityManaBurst"))
		{
			transformations++;
			return patchManaBurst(basicClass);
		}

		return basicClass;
	}

	private byte[] patchManaBurst(byte[] basicClass) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found EntityManaBurst Class: " + classNode.name);

		MethodNode playerConstructor = null;

		for (MethodNode mn : classNode.methods)
		{
			//The constructor that takes a player and a hand
			if (mn.desc.equals("(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/EnumHand;)V"))
			{
				playerConstructor = mn;
			}
		}

		if (playerConstructor != null)
		{
			logger.log(Level.DEBUG, "- Found playerConstructor (4/5)");

			InsnList toInsert = new InsnList();

			AbstractInsnNode thisCallNode = null;
			for (int i = 0; i < playerConstructor.instructions.size(); i++)
			{
				AbstractInsnNode ain = playerConstructor.instructions.get(i);

				if (ain.getOpcode() == INVOKESPECIAL)
				{
					thisCallNode = ain;
				}
			}

			//Load the Player onto the stack
			toInsert.add(new VarInsnNode(ALOAD, 0));
			toInsert.add(new VarInsnNode(ALOAD, 1));
			//Set it to the thrower field
			toInsert.add(new FieldInsnNode(PUTFIELD, "net/minecraft/entity/projectile/EntityThrowable", new ObfuscatedName("field_70192_c").get(), "Lnet/minecraft/entity/EntityLivingBase;"));
//			toInsert.add(new FieldInsnNode(PUTFIELD, "vazkii/botania/common/entity/EntityManaBurst", new ObfuscatedName("field_70192_c").get(), "Lnet/minecraft/entity/EntityPlayer;"));
			toInsert.add(new LabelNode(new Label()));
			playerConstructor.instructions.insert(thisCallNode, toInsert);
		}

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}

	private byte[] patchWorldClass(byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found World Class: " + classNode.name);

		MethodNode getRedstonePower = null;
		MethodNode getStrongPower = null;
		MethodNode isRainingAt = null;
		MethodNode canSnowAt = null;
		MethodNode playSound = null;

		for (MethodNode mn : classNode.methods)
		{
			if (mn.name.equals(new ObfuscatedName("func_175727_C").get()))
			{
				isRainingAt = mn;
			}
			else if (mn.name.equals(new ObfuscatedName("func_175708_f").get()))
			{
				canSnowAt = mn;
			}
		}

		if (isRainingAt != null)
		{
			logger.log(Level.DEBUG, "- Found isRainingAt (3/5)");

			AbstractInsnNode returnNode = null;
			for (int i = 0; i < isRainingAt.instructions.size(); i++)
			{
				AbstractInsnNode ain = isRainingAt.instructions.get(i);

				if (ain.getOpcode() == Opcodes.IRETURN)
				{
					returnNode = ain;
				}
			}

			InsnList toInsert = new InsnList();
			LabelNode returnLabel = new LabelNode(new Label());

			toInsert.add(new InsnNode(Opcodes.DUP));
			toInsert.add(new JumpInsnNode(IFEQ, returnLabel));
			toInsert.add(new InsnNode(POP));
			toInsert.add(new VarInsnNode(ALOAD, 0));
			toInsert.add(new VarInsnNode(ALOAD, 1));
			toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "shouldRain", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z", false));
			toInsert.add(returnLabel);

			isRainingAt.instructions.insertBefore(returnNode, toInsert);

		}

		if (canSnowAt != null)
		{
			logger.log(Level.DEBUG, "- Found canSnowAt (4/5)");

			AbstractInsnNode returnNode = null;
			for (int i = 0; i < canSnowAt.instructions.size(); i++)
			{
				AbstractInsnNode ain = canSnowAt.instructions.get(i);

				if (ain.getOpcode() == Opcodes.IRETURN)
				{
					returnNode = ain;
				}
			}

			InsnList toInsert = new InsnList();
			LabelNode returnLabel = new LabelNode(new Label());

			toInsert.add(new InsnNode(Opcodes.DUP));
			toInsert.add(new JumpInsnNode(IFEQ, returnLabel));
			toInsert.add(new InsnNode(POP));
			toInsert.add(new VarInsnNode(ALOAD, 0));
			toInsert.add(new VarInsnNode(ALOAD, 1));
			toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "canSnowAt", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z", false));
			toInsert.add(returnLabel);

			canSnowAt.instructions.insertBefore(returnNode, toInsert);
		}

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}

	private byte[] patchEntityRenderer(byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found EntityRenderer Class: " + classNode.name);

		MethodNode renderRainSnow = null;
		MethodNode addRainParticles = null;

		for (MethodNode mn : classNode.methods)
		{
			if (mn.name.equals(new ObfuscatedName("func_78474_d").get()))
			{
				renderRainSnow = mn;
			}
			else if (mn.name.equals(new ObfuscatedName("func_78484_h").get()))
			{
				addRainParticles = mn;
			}
		}

		if (renderRainSnow != null)
		{
			logger.log(Level.DEBUG, "- Found renderRainSnow");

			VarInsnNode insnPoint = null;
			for (int i = 0; i < renderRainSnow.instructions.size(); i++)
			{
				AbstractInsnNode ain = renderRainSnow.instructions.get(i);
				if (ain instanceof MethodInsnNode)
				{
					MethodInsnNode min = (MethodInsnNode) ain;

					if (min.name.equals(new ObfuscatedName("func_76738_d").get()))
					{
						logger.log(Level.DEBUG, "- Found canRain");

						insnPoint = (VarInsnNode) renderRainSnow.instructions.get(i - 1);
					}

					if (min.name.equals(new ObfuscatedName("func_76746_c").get()))
					{
						logger.log(Level.DEBUG, "- Found getEnableSnow");
						int jumpCounter = i + 1;

						int worldIndex = 5;
						int blockPosIndex = 21;

						// Optifine Why :'(
						for (LocalVariableNode lv : renderRainSnow.localVariables)
						{
							if (lv.desc.equals("Lnet/minecraft/client/multiplayer/WorldClient;") || lv.desc.equals("Lnet/minecraft/world/World;"))
							{
								worldIndex = lv.index;
							}
							else if (lv.desc.equals("Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"))
							{
								blockPosIndex = lv.index;
							}
						}

						AbstractInsnNode jumpNode;

						while (!((jumpNode = renderRainSnow.instructions.get(jumpCounter)) instanceof JumpInsnNode))
						{
							jumpCounter++;
						}

						JumpInsnNode jin = (JumpInsnNode) jumpNode;
						LabelNode labelNode = jin.label;

						InsnList toInsert = new InsnList();
						toInsert.add(new VarInsnNode(ALOAD, worldIndex));
						toInsert.add(new VarInsnNode(ALOAD, blockPosIndex));
						toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "shouldRain", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z", false));
						toInsert.add(new JumpInsnNode(IFEQ, labelNode));
						renderRainSnow.instructions.insertBefore(insnPoint, toInsert);
						i += 4;
					}

					if (min.name.equals("shouldRenderRainSnow") && min.owner.equals("sereneseasons/season/SeasonASMHelper"))
					{
						// Serene Seasons Compability
						logger.log(Level.DEBUG, "- Found Serene Seasons shouldRenderRainSnow");

						int worldIndex = 5;
						int blockPosIndex = 21;

						// Optifine Why :'(
						for (LocalVariableNode lv : renderRainSnow.localVariables)
						{
							if (lv.desc.equals("Lnet/minecraft/client/multiplayer/WorldClient;") || lv.desc.equals("Lnet/minecraft/world/World;"))
							{
								worldIndex = lv.index;
							}
							else if (lv.desc.equals("Lnet/minecraft/util/math/BlockPos$MutableBlockPos;"))
							{
								blockPosIndex = lv.index;
							}
						}

						LabelNode l0 = new LabelNode();

						InsnList toInsert = new InsnList();
						toInsert.add(new VarInsnNode(ALOAD, worldIndex));
						toInsert.add(new VarInsnNode(ALOAD, blockPosIndex));
						toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "shouldRain", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z", false));
						toInsert.add(new JumpInsnNode(IFNE, l0));
						toInsert.add(new InsnNode(POP));
						toInsert.add(new IntInsnNode(BIPUSH, 0));
						toInsert.add(l0);

						renderRainSnow.instructions.insert(min, toInsert);
						i += 7;
					}
				}
			}
		}

		if (addRainParticles != null)
		{
			logger.log(Level.DEBUG, "- Found addRainParticles");

			for (int i = 0; i < addRainParticles.instructions.size(); i++)
			{
				AbstractInsnNode ain = addRainParticles.instructions.get(i);
				if (ain instanceof JumpInsnNode)
				{
					JumpInsnNode jin = (JumpInsnNode) ain;
					if (jin.getOpcode() == Opcodes.IF_ICMPGT)
					{
						LabelNode jumpTarget = jin.label;

						InsnList toInsert = new InsnList();
						toInsert.add(new VarInsnNode(ALOAD, 3));
						toInsert.add(new VarInsnNode(ALOAD, 15));
						toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "shouldRain", "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z", false));
						toInsert.add(new JumpInsnNode(IFEQ, jumpTarget));

						addRainParticles.instructions.insert(jin, toInsert);

						break;
					}
				}
			}
		}

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}

//
//	private byte[] patchEntityLivingBase(byte[] basicClass)
//	{
//		ClassNode classNode = new ClassNode();
//		ClassReader classReader = new ClassReader(basicClass);
//		classReader.accept(classNode, 0);
//		logger.log(Level.DEBUG, "Found EntityLivingBase Class: " + classNode.name);
//
//		MethodNode updatePotionEffects = null;
//		MethodNode travel = null;
//
//		ObfuscatedName travelFunc = new ObfuscatedName("func_191986_a");
//
//		for (MethodNode mn : classNode.methods)
//		{
//			if (mn.name.equals(travelFunc.get()))
//			{
//				travel = mn;
//			}
//		}
//
//		if (travel != null)
//		{
//
//			ObfuscatedName entityWorld = new ObfuscatedName("field_70170_p");
//			ObfuscatedName getBlockState = new ObfuscatedName("func_180495_p");
//			ObfuscatedName getBlock = new ObfuscatedName("func_177230_c");
//
//			//For each instruction
//			for (int i = 0; i < travel.instructions.size(); i++) {
//				AbstractInsnNode ain = travel.instructions.get(i);
//				//if it's pushing something to the stack
//				if (ain instanceof LdcInsnNode)
//				{
//					LdcInsnNode lin = (LdcInsnNode) ain;
//					//and that thing is 0.91f
//					if (lin.cst.equals(new Float("0.91")))
//					{
//						AbstractInsnNode next = travel.instructions.get(i + 1);
//						//and we're then multiplying floats - the operation after our insertion point will be the storing of the value
//						if (next.getOpcode() == Opcodes.FMUL)
//						{
//							//We're in the right place -
//							InsnList toInsert = new InsnList();
//							//Load the entity twice onto the stack
//							toInsert.add(new VarInsnNode(ALOAD, 0));
//							toInsert.add(new VarInsnNode(ALOAD, 0));
//							//Get World from the entity
//							toInsert.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/EntityLivingBase", entityWorld.get(), "Lnet/minecraft/world/World;"));
//							//Load the block position onto the stack
//							toInsert.add(new VarInsnNode(ALOAD, 5));
//							//Call getBlockState, leaving a BlockState on the stack
//							toInsert.add(new MethodInsnNode(INVOKEVIRTUAL, "net/minecraft/world/World", getBlockState.get(), "(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;", false));
//							//Call getBlock, leaving a Block on the stack
//							toInsert.add(new MethodInsnNode(INVOKEINTERFACE, "net/minecraft/block/state/IBlockState", getBlock.get(), "()Lnet/minecraft/block/Block;", true));
//							//Call our code, I think leaving the return value to be stored
//							toInsert.add(new MethodInsnNode(INVOKESTATIC, asmHandler, "slipFix", "(FLnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/block/Block;)F", false));
//
//							//jam the instructions in and skip over them - no need to read the things we just added
//							travel.instructions.insert(next, toInsert);
//							i += 6;
//						}
//					}
//				}
//			}
//		}
//
//		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
//		classNode.accept(writer);
//
//		return writer.toByteArray();
//	}


	private byte[] patchDummyClass(byte[] basicClass)
	{
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);
		logger.log(Level.DEBUG, "Found Dummy Class: " + classNode.name);

		CustomClassWriter writer = new CustomClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		return writer.toByteArray();
	}

	public int getNextIndex(MethodNode mn)
	{
		Iterator it = mn.localVariables.iterator();
		int max = 0;
		int next = 0;
		while (it.hasNext())
		{
			LocalVariableNode var = (LocalVariableNode) it.next();
			int index = var.index;
			if (index >= max)
			{
				max = index;
				next = max + Type.getType(var.desc).getSize();
			}
		}
		return next;
	}
}

package blue.thejester.botanybooster.client.model;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.actors.$bang;

@SideOnly(Side.CLIENT)
public class SphereRenderer {

    private static final double PHI = (Math.sqrt(5) + 1) / 2;
    private static final Vec3d[][] tris2;

    static {
        Vec3d[] vertices = {
                new Vec3d(1, PHI, 0),
                new Vec3d(-1, PHI, 0),
                new Vec3d(0, 1, PHI),
                new Vec3d(0, 1, -PHI),
                new Vec3d(PHI, 0, 1),
                new Vec3d(PHI, 0, -1),
                new Vec3d(-PHI, 0, 1),
                new Vec3d(-PHI, 0, -1),
                new Vec3d(0, -1, PHI),
                new Vec3d(0, -1, -PHI),
                new Vec3d(1, -PHI, 0),
                new Vec3d(-1, -PHI, 0)
        };
        Vec3d[][] tris =
                {
                        {vertices[7],vertices[3],vertices[9]},
                        {vertices[3],vertices[5],vertices[9]},
                        {vertices[5],vertices[10],vertices[9]},
                        {vertices[10],vertices[11],vertices[9]},
                        {vertices[11],vertices[7],vertices[9]},
                        {vertices[7],vertices[11],vertices[6]},
                        {vertices[6],vertices[1],vertices[7]},
                        {vertices[1],vertices[3],vertices[7]},
                        {vertices[1],vertices[0],vertices[3]},
                        {vertices[0],vertices[5],vertices[3]},
                        {vertices[0],vertices[4],vertices[5]},
                        {vertices[10],vertices[5],vertices[4]},
                        {vertices[10],vertices[4],vertices[8]},
                        {vertices[11],vertices[10],vertices[8]},
                        {vertices[6],vertices[11],vertices[8]},
                        {vertices[2],vertices[6],vertices[8]},
                        {vertices[1],vertices[6],vertices[2]},
                        {vertices[0],vertices[1],vertices[2]},
                        {vertices[4],vertices[0],vertices[2]},
                        {vertices[8],vertices[4],vertices[2]},
                };
        Vec3d[][] tris1 = new Vec3d[tris.length * 4][3];
        int idx = 0;
        for(Vec3d[] tri : tris) {
            double circumradius = tri[0].length();
            Vec3d a0 = tri[0].add(tri[1]).normalize().scale(circumradius);
            Vec3d a1 = tri[1].add(tri[2]).normalize().scale(circumradius);
            Vec3d a2 = tri[2].add(tri[0]).normalize().scale(circumradius);
            tris1[idx++] = new Vec3d[] {tri[0], a0, a2};
            tris1[idx++] = new Vec3d[] {a0, a1, a2};
            tris1[idx++] = new Vec3d[] {tri[2], a2, a1};
            tris1[idx++] = new Vec3d[] {a0, tri[1], a1};
        }

        tris2 = new Vec3d[tris1.length * 4][3];
        idx = 0;
        for(Vec3d[] tri : tris1) {
            double circumradius = tri[0].length();
            Vec3d a0 = tri[0].add(tri[1]).normalize().scale(circumradius);
            Vec3d a1 = tri[1].add(tri[2]).normalize().scale(circumradius);
            Vec3d a2 = tri[2].add(tri[0]).normalize().scale(circumradius);
            tris2[idx++] = new Vec3d[] {tri[0], a0, a2};
            tris2[idx++] = new Vec3d[] {a0, a1, a2};
            tris2[idx++] = new Vec3d[] {tri[2], a2, a1};
            tris2[idx++] = new Vec3d[] {a0, tri[1], a1};
        }
    }

    public static void insertVertices(BufferBuilder bb) {
        for(Vec3d[] tri : tris2) {
            Vec3d v = tri[0];
            bb.pos(v.x, v.y, v.z).tex(0, 0).endVertex();
            v = tri[1];
            bb.pos(v.x, v.y, v.z).tex(0, 1).endVertex();
            v = tri[2];
            bb.pos(v.x, v.y, v.z).tex(1, 1).endVertex();
        }
    }
}
